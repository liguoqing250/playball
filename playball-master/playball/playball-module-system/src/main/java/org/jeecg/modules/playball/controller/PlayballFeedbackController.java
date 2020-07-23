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
import org.jeecg.common.playball.entity.PlayballFeedback;
import org.jeecg.modules.playball.service.IPlayballFeedbackService;
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
 * @Description: 意见反馈
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
@Slf4j
@Api(tags="意见反馈")
@RestController
@RequestMapping("/playball/playballFeedback")
public class PlayballFeedbackController extends JeecgController<PlayballFeedback, IPlayballFeedbackService> {
	@Autowired
	private IPlayballFeedbackService playballFeedbackService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballFeedback
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "意见反馈-分页列表查询")
	@ApiOperation(value="意见反馈-分页列表查询", notes="意见反馈-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballFeedback playballFeedback,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PlayballFeedback> queryWrapper = QueryGenerator.initQueryWrapper(playballFeedback, req.getParameterMap());
		Page<PlayballFeedback> page = new Page<PlayballFeedback>(pageNo, pageSize);
		IPage<PlayballFeedback> pageList = playballFeedbackService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param playballFeedback
	 * @return
	 */
	@AutoLog(value = "意见反馈-添加")
	@ApiOperation(value="意见反馈-添加", notes="意见反馈-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballFeedback playballFeedback) {
		playballFeedbackService.save(playballFeedback);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballFeedback
	 * @return
	 */
	@AutoLog(value = "意见反馈-编辑")
	@ApiOperation(value="意见反馈-编辑", notes="意见反馈-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballFeedback playballFeedback) {
		playballFeedbackService.updateById(playballFeedback);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "意见反馈-通过id删除")
	@ApiOperation(value="意见反馈-通过id删除", notes="意见反馈-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		playballFeedbackService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "意见反馈-批量删除")
	@ApiOperation(value="意见反馈-批量删除", notes="意见反馈-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.playballFeedbackService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "意见反馈-通过id查询")
	@ApiOperation(value="意见反馈-通过id查询", notes="意见反馈-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballFeedback playballFeedback = playballFeedbackService.getById(id);
		return Result.ok(playballFeedback);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballFeedback
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballFeedback playballFeedback) {
      return super.exportXls(request, playballFeedback, PlayballFeedback.class, "意见反馈");
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
      return super.importExcel(request, response, PlayballFeedback.class);
  }

}
