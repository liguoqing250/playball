package org.jeecg.modules.playball.controller;

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
import org.jeecg.modules.playball.entity.PlayballSchedule;
import org.jeecg.modules.playball.service.IPlayballScheduleService;
import org.jeecg.modules.playball.vo.PlayballScheduleInfoPage;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 赛程表
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
@Slf4j
@Api(tags="赛程表")
@RestController
@RequestMapping("/playball/playballSchedule")
public class PlayballScheduleController extends JeecgController<PlayballSchedule, IPlayballScheduleService> {
	@Autowired
	private IPlayballScheduleService playballScheduleService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballSchedule
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "赛程表-分页列表查询")
	@ApiOperation(value="赛程表-分页列表查询", notes="赛程表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballScheduleInfoPage playballScheduleInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		/*QueryWrapper<PlayballSchedule> queryWrapper = QueryGenerator.initQueryWrapper(playballSchedule, req.getParameterMap());
		Page<PlayballSchedule> page = new Page<PlayballSchedule>(pageNo, pageSize);
		IPage<PlayballSchedule> pageList = playballScheduleService.page(page, queryWrapper);*/
		
		IPage<PlayballScheduleInfoPage> pageList = playballScheduleService.querySchedulePageList(new Page(pageNo, pageSize), playballScheduleInfo);
	       
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param playballSchedule
	 * @return
	 */
	@AutoLog(value = "赛程表-添加")
	@ApiOperation(value="赛程表-添加", notes="赛程表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballSchedule playballSchedule) {
		//playballScheduleService.save(playballSchedule);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballSchedule
	 * @return
	 */
	@AutoLog(value = "赛程表-编辑")
	@ApiOperation(value="赛程表-编辑", notes="赛程表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballSchedule playballSchedule) {
		//playballScheduleService.updateById(playballSchedule);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "赛程表-通过id删除")
	@ApiOperation(value="赛程表-通过id删除", notes="赛程表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		//playballScheduleService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "赛程表-批量删除")
	@ApiOperation(value="赛程表-批量删除", notes="赛程表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		//this.playballScheduleService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "赛程表-通过id查询")
	@ApiOperation(value="赛程表-通过id查询", notes="赛程表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballSchedule playballSchedule = playballScheduleService.getById(id);
		return Result.ok(playballSchedule);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballSchedule
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballSchedule playballSchedule) {
      return super.exportXls(request, playballSchedule, PlayballSchedule.class, "赛程表");
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
      return super.importExcel(request, response, PlayballSchedule.class);
  }

}
