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
import org.jeecg.modules.appapi.entity.ScheduleUserdata;
import org.jeecg.modules.appapi.service.IScheduleUserdataService;
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
 * @Description: 赛程球员数据
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
@Slf4j
@Api(tags="赛程球员数据")
@RestController
@RequestMapping("/ScheduleUserdata")
public class ScheduleUserdataController extends JeecgController<ScheduleUserdata, IScheduleUserdataService> {
	@Autowired
	private IScheduleUserdataService scheduleUserdataService;
	
	/**
	 * 分页列表查询
	 *
	 * @param scheduleUserdata
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="赛程球员数据-分页列表查询", notes="赛程球员数据-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(ScheduleUserdata scheduleUserdata,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ScheduleUserdata> queryWrapper = QueryGenerator.initQueryWrapper(scheduleUserdata, req.getParameterMap());
		Page<ScheduleUserdata> page = new Page<ScheduleUserdata>(pageNo, pageSize);
		IPage<ScheduleUserdata> pageList = scheduleUserdataService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param scheduleUserdata
	 * @return
	 */
	@ApiOperation(value="赛程球员数据-添加", notes="赛程球员数据-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ScheduleUserdata scheduleUserdata) {
		scheduleUserdataService.save(scheduleUserdata);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param scheduleUserdata
	 * @return
	 */
	@ApiOperation(value="赛程球员数据-编辑", notes="赛程球员数据-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ScheduleUserdata scheduleUserdata) {
		scheduleUserdataService.updateById(scheduleUserdata);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="赛程球员数据-通过id删除", notes="赛程球员数据-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		scheduleUserdataService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="赛程球员数据-批量删除", notes="赛程球员数据-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.scheduleUserdataService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="赛程球员数据-通过id查询", notes="赛程球员数据-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ScheduleUserdata scheduleUserdata = scheduleUserdataService.getById(id);
		return Result.ok(scheduleUserdata);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param scheduleUserdata
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, ScheduleUserdata scheduleUserdata) {
      return super.exportXls(request, scheduleUserdata, ScheduleUserdata.class, "赛程球员数据");
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
      return super.importExcel(request, response, ScheduleUserdata.class);
  }

}
