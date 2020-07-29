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
import org.jeecg.common.playball.entity.PlayballDynamic;
import org.jeecg.common.playball.vo.PlayballDynamicModel;
import org.jeecg.modules.playball.service.IPlayballDynamicService;
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
 * @Description: 用户动态发布
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
@Slf4j
@Api(tags="用户动态发布")
@RestController
@RequestMapping("/playball/playballDynamic")
public class PlayballDynamicController extends JeecgController<PlayballDynamic, IPlayballDynamicService> {
	@Autowired
	private IPlayballDynamicService playballDynamicService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballDynamic
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "用户动态发布-分页列表查询")
	@ApiOperation(value="用户动态发布-分页列表查询", notes="用户动态发布-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballDynamicModel playballDynamicModel,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		/*QueryWrapper<PlayballDynamic> queryWrapper = QueryGenerator.initQueryWrapper(playballDynamic, req.getParameterMap());
		Page<PlayballDynamic> page = new Page<PlayballDynamic>(pageNo, pageSize);
		IPage<PlayballDynamic> pageList = playballDynamicService.page(page, queryWrapper);*/
		
		IPage<PlayballDynamicModel> pageList = playballDynamicService.queryDynamicList(new Page(pageNo,pageSize), playballDynamicModel);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param playballDynamic
	 * @return
	 */
	@AutoLog(value = "用户动态发布-添加")
	@ApiOperation(value="用户动态发布-添加", notes="用户动态发布-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballDynamic playballDynamic) {
		playballDynamicService.save(playballDynamic);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballDynamic
	 * @return
	 */
	@AutoLog(value = "用户动态发布-编辑")
	@ApiOperation(value="用户动态发布-编辑", notes="用户动态发布-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballDynamic playballDynamic) {
		playballDynamicService.updateById(playballDynamic);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户动态发布-通过id删除")
	@ApiOperation(value="用户动态发布-通过id删除", notes="用户动态发布-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		playballDynamicService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "用户动态发布-批量删除")
	@ApiOperation(value="用户动态发布-批量删除", notes="用户动态发布-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.playballDynamicService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "用户动态发布-通过id查询")
	@ApiOperation(value="用户动态发布-通过id查询", notes="用户动态发布-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballDynamic playballDynamic = playballDynamicService.getById(id);
		return Result.ok(playballDynamic);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballDynamic
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballDynamic playballDynamic) {
      return super.exportXls(request, playballDynamic, PlayballDynamic.class, "用户动态发布");
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
      return super.importExcel(request, response, PlayballDynamic.class);
  }

}
