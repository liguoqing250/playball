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
import org.jeecg.modules.playball.entity.PlayballCourse;
import org.jeecg.modules.playball.service.IPlayballCourseService;
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
 * @Description: cms教程
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Slf4j
@Api(tags="cms教程")
@RestController
@RequestMapping("/playball/playballCourse")
public class PlayballCourseController extends JeecgController<PlayballCourse, IPlayballCourseService> {
	@Autowired
	private IPlayballCourseService playballCourseService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballCourse
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "cms教程-分页列表查询")
	@ApiOperation(value="cms教程-分页列表查询", notes="cms教程-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballCourse playballCourse,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PlayballCourse> queryWrapper = QueryGenerator.initQueryWrapper(playballCourse, req.getParameterMap());
		log.info("--------------------cms教程-----------------");
		Page<PlayballCourse> page = new Page<PlayballCourse>(pageNo, pageSize);
		IPage<PlayballCourse> pageList = playballCourseService.page(page, queryWrapper);
		log.info("--------------------cms教程"+pageList);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param playballCourse
	 * @return
	 */
	@AutoLog(value = "cms教程-添加")
	@ApiOperation(value="cms教程-添加", notes="cms教程-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballCourse playballCourse) {
		playballCourseService.save(playballCourse);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballCourse
	 * @return
	 */
	@AutoLog(value = "cms教程-编辑")
	@ApiOperation(value="cms教程-编辑", notes="cms教程-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballCourse playballCourse) {
		playballCourseService.updateById(playballCourse);
		//playballCourseService.updateCourseById(playballCourse);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "cms教程-通过id删除")
	@ApiOperation(value="cms教程-通过id删除", notes="cms教程-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		playballCourseService.removeById(id);
		//playballCourseService.deleteById(Integer.parseInt(id));
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "cms教程-批量删除")
	@ApiOperation(value="cms教程-批量删除", notes="cms教程-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.playballCourseService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "cms教程-通过id查询")
	@ApiOperation(value="cms教程-通过id查询", notes="cms教程-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballCourse playballCourse = playballCourseService.getById(id);
		return Result.ok(playballCourse);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballCourse
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballCourse playballCourse) {
      return super.exportXls(request, playballCourse, PlayballCourse.class, "cms教程");
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
      return super.importExcel(request, response, PlayballCourse.class);
  }

}
