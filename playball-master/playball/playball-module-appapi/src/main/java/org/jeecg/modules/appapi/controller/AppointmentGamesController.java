package org.jeecg.modules.appapi.controller;

import java.security.AccessControlContext;
import java.util.*;
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
import org.jeecg.modules.appapi.entity.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.appapi.entity.bo.AppointmentGamesBo;
import org.jeecg.modules.appapi.entity.vo.AppointmentGamesVo;
import org.jeecg.modules.appapi.entity.vo.UserCommentVo;
import org.jeecg.modules.appapi.mapper.AppTeamMapper;
import org.jeecg.modules.appapi.mapper.AppointmentGamesMapper;
import org.jeecg.modules.appapi.service.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 约球
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="约球")
@RestController
@RequestMapping("/AppointmentGames")
public class AppointmentGamesController extends JeecgController<AppointmentGames, IAppointmentGamesService> {
	 @Autowired
	 private IAppointmentGamesService appointmentGamesService;
	 @Autowired
	 private AppointmentGamesMapper appointmentGamesMapper;
	 @Autowired
	 private IAcceptAppointmentService iAcceptAppointmentService;
	 @Autowired
	 private AppTeamMapper appTeamMapper;
	 @Autowired
	 private AppUsersService appUsersService;

	 @Autowired
	 private IFieldInfoService fieldInfoService;

	@Autowired
	private HttpServletRequest request;
	 /**
	  * 分页查询用户已参加约球
	  *
	  * @return
	  */
	 @ApiOperation(value="约球-分页查询用户已参加约球", notes="约球-分页查询用户已参加约球")
	 @PostMapping(value = "/listByUid")
	 public Result<?> listByUid(AppointmentGamesVo appointmentGamesVo) {
		 //分页
		 Page<AppointmentGamesVo> page = new Page<>(appointmentGamesVo.getPage(), appointmentGamesVo.getLimit());
		 //查询条件
		 IPage<AppointmentGamesBo> selectPage = appointmentGamesMapper.listByUid(page, appointmentGamesVo.getUId());
		 return Result.ok(selectPage);
	 }

	/**
	 * 分页列表查询
	 *
	 * @param appointmentGames
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="约球-分页列表查询", notes="约球-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(AppointmentGames appointmentGames,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AppointmentGames> queryWrapper = QueryGenerator.initQueryWrapper(appointmentGames, req.getParameterMap());
		Page<AppointmentGames> page = new Page<AppointmentGames>(pageNo, pageSize);
		IPage<AppointmentGames> pageList = appointmentGamesService.page(page, queryWrapper);
		for (int i = 0; i <pageList.getRecords().size() ; i++) {
			pageList.getRecords().get(i).setUserInfo(appUsersService.selectById(pageList.getRecords().get(i).getAgUserId()));
			if(iAcceptAppointmentService.isJoinAppointment(pageList.getRecords().get(i).getId())){
				pageList.getRecords().get(i).setAgStatus(3);
			}else{
				if(pageList.getRecords().get(i).getAgAppointmentTime().getTime()<new Date().getTime() ){
					pageList.getRecords().get(i).setAgStatus(2);
				}else if(pageList.getRecords().get(i).getAgAppointmentTime().getTime()>new Date().getTime() ){
					pageList.getRecords().get(i).setAgStatus(1);
				}
			}
		}
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param appointmentGames
	 * @return
	 */
	@ApiOperation(value="约球-添加", notes="约球-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AppointmentGames appointmentGames) {
		String token=request.getHeader("X-Access-Token");
		AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
		FieldInfo fieldInfo = fieldInfoService.getById(appointmentGames.getfId());
		appointmentGames.setAgUserId(appUsers.getU_id());
		appointmentGames.setAgStatus(1);
		appointmentGames.setbId(fieldInfo.getBusinessId());
		AppTeam appTeam=appTeamMapper.isCaptain(appUsers.getU_id());
		if(appointmentGames.getAppointmentWay()==1){
			//个人
			appointmentGames.setAgSubjectId(appUsers.getU_id());
		}else{
			//团队

			appointmentGames.setTpId(appTeam.getTeam_id());
		}
		appointmentGamesService.save(appointmentGames);
		AcceptAppointment acceptAppointment=new AcceptAppointment();
		acceptAppointment.setAgId(appointmentGames.getId());
		if(appointmentGames.getAppointmentWay()==1){
			acceptAppointment.setAaAcceptSubjectId(appUsers.getU_id().toString());
		}else{
			acceptAppointment.setAaAcceptSubjectId(appTeam.getTeam_id().toString());
		}
		iAcceptAppointmentService.save(acceptAppointment);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param appointmentGames
	 * @return
	 */
	@ApiOperation(value="约球-编辑", notes="约球-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AppointmentGames appointmentGames) {
		appointmentGamesService.updateById(appointmentGames);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="约球-通过id删除", notes="约球-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		appointmentGamesService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="约球-批量删除", notes="约球-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.appointmentGamesService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="约球-通过id查询", notes="约球-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AppointmentGames appointmentGames = appointmentGamesService.getById(id);
		return Result.ok(appointmentGames);
	}

	 /**
	  * 通过查询应约球队或个人
	  *
	  * @param id
	  * @return
	  */
	 @ApiOperation(value="约球-通过id查询", notes="约球-通过id查询")
	 @PostMapping(value = "/queryJoinAppointmentGamesPlayers")
	 public Result<?> queryJoinAppointmentGamesPlayers(@RequestParam(name="id",required=true) String id) {
	 	List<JoinAppointmentGamesPlayer> joinAppointmentGamesPlayers=appointmentGamesService.queryJoinAppointmentGamesPlayers(id);
		 return Result.ok(joinAppointmentGamesPlayers);
	 }
  /**
   * 导出excel
   *
   * @param request
   * @param appointmentGames
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, AppointmentGames appointmentGames) {
      return super.exportXls(request, appointmentGames, AppointmentGames.class, "约球");
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
      return super.importExcel(request, response, AppointmentGames.class);
  }

}
