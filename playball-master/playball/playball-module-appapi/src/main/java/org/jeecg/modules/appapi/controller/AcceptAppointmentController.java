package org.jeecg.modules.appapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.appapi.entity.AcceptAppointment;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.AppointmentGames;
import org.jeecg.modules.appapi.service.AppTeamService;
import org.jeecg.modules.appapi.service.IAcceptAppointmentService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.appapi.service.IAppointmentGamesService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 应约
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="应约")
@RestController
@RequestMapping("/AcceptAppointment")
public class AcceptAppointmentController extends JeecgController<AcceptAppointment,IAcceptAppointmentService> {
	@Autowired
	private IAcceptAppointmentService acceptAppointmentService;
	 @Autowired
	 AppTeamService appTeamService;
	 @Autowired
	 IAppointmentGamesService iAppointmentGamesService;
	/**
	 * 分页列表查询
	 *
	 * @param acceptAppointment
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="应约-分页列表查询", notes="应约-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(AcceptAppointment acceptAppointment,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AcceptAppointment> queryWrapper = QueryGenerator.initQueryWrapper(acceptAppointment, req.getParameterMap());
		Page<AcceptAppointment> page = new Page<AcceptAppointment>(pageNo, pageSize);
		IPage<AcceptAppointment> pageList = acceptAppointmentService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param acceptAppointment
	 * @return
	 */
	@ApiOperation(value="应约-添加", notes="应约-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AcceptAppointment acceptAppointment) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String token=request.getHeader("X-Access-Token");
		AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);

		AppointmentGames appointmentGames=iAppointmentGamesService.getById(acceptAppointment.getAgId());
		if(!acceptAppointmentService.isJoinAppointment(acceptAppointment.getAgId())){
			if(appointmentGames.getTpId()!=null){
				//球队约球
				if(appTeamService.isJoinTeam()){
					AppTeam appTeam= appTeamService.selectMyTeamInfo();
					if(appTeamService.isCaptain()){
						acceptAppointment.setAaAcceptSubjectId(appTeam.getTeam_id().toString());
						acceptAppointmentService.save(acceptAppointment);

						return Result.ok("约球成功！");
					}else{
						return Result.error("您不是队长不能参与球队约球");
					}
				}else{

					return Result.error("您还没有球队不能参与球队约球");
				}
			}else{
				//个人约球
				acceptAppointment.setAaAcceptSubjectId(appUsers.getU_id().toString());
				acceptAppointmentService.save(acceptAppointment);

				return Result.ok("约球成功！");
			}
		}else{
			return Result.error("您已参与该约球");
		}




	}
	
	/**
	 * 编辑
	 *
	 * @param acceptAppointment
	 * @return
	 */
	@ApiOperation(value="应约-编辑", notes="应约-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AcceptAppointment acceptAppointment) {
		acceptAppointmentService.updateById(acceptAppointment);
		return Result.ok("编辑成功!");
	}

	 /**
	  * 是否已参与约球
	  *
	  * @param id
	  * @return
	  */
	 @ApiOperation(value="应约-编辑", notes="应约-编辑")
	 @PostMapping(value = "/isJoinAppointment")
	 public Result<?> isJoinAppointment(Integer id) {
	 	if(acceptAppointmentService.isJoinAppointment(id)){
			return Result.ok(true);
		}
		 return Result.ok(false);
	 }
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="应约-通过id删除", notes="应约-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		acceptAppointmentService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="应约-批量删除", notes="应约-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.acceptAppointmentService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="应约-通过id查询", notes="应约-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AcceptAppointment acceptAppointment = acceptAppointmentService.getById(id);
		return Result.ok(acceptAppointment);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param acceptAppointment
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, AcceptAppointment acceptAppointment) {
      return super.exportXls(request, acceptAppointment, AcceptAppointment.class, "应约");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      return super.importExcel(request, response, AcceptAppointment.class);
  }

}
