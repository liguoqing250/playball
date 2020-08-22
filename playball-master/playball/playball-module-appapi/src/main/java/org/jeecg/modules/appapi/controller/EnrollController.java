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
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.Enroll;
import org.jeecg.modules.appapi.entity.Game;
import org.jeecg.modules.appapi.service.AppTeamService;
import org.jeecg.modules.appapi.service.IEnrollService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.appapi.service.IGameService;
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
 * @Description: 比赛报名表
 * @Author: jeecg-boot
 * @Date:   2020-07-11
 * @Version: V1.0
 */
@Slf4j
@Api(tags="比赛报名表")
@RestController
@RequestMapping("/Enroll")
public class EnrollController extends JeecgController<Enroll, IEnrollService> {
	@Autowired
	private IEnrollService enrollService;
	@Autowired
	 AppTeamService appTeamService;
	 @Autowired
	 private IGameService gameService;
	/**
	 * 分页列表查询
	 *
	 * @param enroll
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="比赛报名表-分页列表查询", notes="比赛报名表-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(Enroll enroll,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Enroll> queryWrapper = QueryGenerator.initQueryWrapper(enroll, req.getParameterMap());
		Page<Enroll> page = new Page<Enroll>(pageNo, pageSize);
		IPage<Enroll> pageList = enrollService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param enroll
	 * @return
	 */
	@ApiOperation(value="比赛报名表-添加", notes="比赛报名表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Enroll enroll,HttpServletRequest req) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String token=request.getHeader("X-Access-Token");
		AppUsers appUsers=JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
		if(appTeamService.isJoinTeam()){
			AppTeam appTeam=appTeamService.selectMyTeamInfo();
			if(appTeam.getT_captain()==appUsers.getU_id()){
				Game game=gameService.getById(enroll.getGamesId());
				if(game.getSportsId()==appTeam.getSt_id()){
					enroll.setTeamId(appTeam.getTeam_id());
				}else{
					return Result.error("不能报名与球队类型不同的比赛!");
				}

			}else{
				return Result.error("只有队长能报名比赛!");

			}

		}else{
			return Result.error("个人不能参与比赛!");
		}
		if(enrollService.hasenroll(enroll)){
			return Result.error("已报名，请勿重复报名!");
		}
		enrollService.save(enroll);

		return Result.ok(enroll);
	}
	
	/**
	 * 编辑
	 *
	 * @param enroll
	 * @return
	 */
	@ApiOperation(value="比赛报名表-编辑", notes="比赛报名表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Enroll enroll) {
		enrollService.updateById(enroll);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="比赛报名表-通过id删除", notes="比赛报名表-通过id删除")
	@PostMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		enrollService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="比赛报名表-批量删除", notes="比赛报名表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.enrollService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="比赛报名表-通过id查询", notes="比赛报名表-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Enroll enroll = enrollService.getById(id);
		return Result.ok(enroll);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param enroll
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, Enroll enroll) {
      return super.exportXls(request, enroll, Enroll.class, "比赛报名表");
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
      return super.importExcel(request, response, Enroll.class);
  }

}
