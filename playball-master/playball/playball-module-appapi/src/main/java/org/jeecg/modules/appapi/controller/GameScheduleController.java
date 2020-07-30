package org.jeecg.modules.appapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules. appapi.entity.GameSchedule;
import org.jeecg.modules.appapi.entity.vo.GameScheduleVo;
import org.jeecg.modules. appapi.service.IGameScheduleService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 比赛
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="比赛")
@RestController
@RequestMapping("/GameSchedule")
public class GameScheduleController extends JeecgController<GameSchedule, IGameScheduleService> {
	@Autowired
	private IGameScheduleService gameScheduleService;

	 /**
	  * 通过team_id查询赛程记录
	  *
	  * @param team_id
	  * @return
	  */
	 @ApiOperation(value="比赛-通过GameId查询赛程", notes="比赛-通过GameId查询赛程")
	 @PostMapping(value = "/queryTeamGameScore")
	 public Result<?> queryTeamGameScore(@RequestParam(name="team_id",required=true) Integer team_id) {
	 	try {
			List<GameScheduleVo>  list = gameScheduleService.queryTeamGameScore(team_id);
			System.err.println(list.size());
			return Result.ok(list);
		}catch (Exception e){
			return Result.error("查询失败");
		}
	 }
	 /**
	  * 通过GameId查询赛程
	  *
	  * @param id
	  * @return
	  */
	 @ApiOperation(value="比赛-通过GameId查询赛程", notes="比赛-通过GameId查询赛程")
	 @PostMapping(value = "/queryByGameId")
	 public Result<?> queryByGameId(@RequestParam(name="id",required=true) String id) {
		 List<GameSchedule>  list = gameScheduleService.queryByGameId(id);
		 return Result.ok(list);
	 }
	/**
	 * 分页列表查询
	 *
	 * @param gameSchedule
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="比赛-分页列表查询", notes="比赛-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(GameSchedule gameSchedule,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<GameSchedule> queryWrapper = QueryGenerator.initQueryWrapper(gameSchedule, req.getParameterMap());
		Page<GameSchedule> page = new Page<GameSchedule>(pageNo, pageSize);
		IPage<GameSchedule> pageList = gameScheduleService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param gameSchedule
	 * @return
	 */
	@ApiOperation(value="比赛-添加", notes="比赛-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody GameSchedule gameSchedule) {
		gameScheduleService.save(gameSchedule);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param gameSchedule
	 * @return
	 */
	@ApiOperation(value="比赛-编辑", notes="比赛-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody GameSchedule gameSchedule) {
		gameScheduleService.updateById(gameSchedule);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="比赛-通过id删除", notes="比赛-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		gameScheduleService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="比赛-批量删除", notes="比赛-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.gameScheduleService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="比赛-通过id查询", notes="比赛-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		GameSchedule gameSchedule = gameScheduleService.getById(id);
		return Result.ok(gameSchedule);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param gameSchedule
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, GameSchedule gameSchedule) {
      return super.exportXls(request, gameSchedule, GameSchedule.class, "比赛");
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
      return super.importExcel(request, response, GameSchedule.class);
  }

}
