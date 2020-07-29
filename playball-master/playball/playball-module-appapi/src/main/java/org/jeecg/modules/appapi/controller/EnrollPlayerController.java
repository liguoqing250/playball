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
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.appapi.entity.EnrollPlayer;
import org.jeecg.modules.appapi.service.IEnrollPlayerService;
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
 * @Description: 参赛球员
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
@Slf4j
@Api(tags="参赛球员")
@RestController
@RequestMapping("/EnrollPlayer")
public class EnrollPlayerController extends JeecgController<EnrollPlayer, IEnrollPlayerService> {
	@Autowired
	private IEnrollPlayerService enrollPlayerService;
	
	/**
	 * 分页列表查询
	 *
	 * @param enrollPlayer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="参赛球员-分页列表查询", notes="参赛球员-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(EnrollPlayer enrollPlayer,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<EnrollPlayer> queryWrapper = QueryGenerator.initQueryWrapper(enrollPlayer, req.getParameterMap());
		Page<EnrollPlayer> page = new Page<EnrollPlayer>(pageNo, pageSize);
		IPage<EnrollPlayer> pageList = enrollPlayerService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	 /**
	  * 批量添加
	  *
	  * @param enrollPlayerList
	  * @return
	  */
	 @ApiOperation(value="参赛球员-添加", notes="参赛球员-添加")
	 @PostMapping(value = "/addList")
	 public Result<?> add(@RequestBody List<EnrollPlayer> enrollPlayerList) {
		 for (int i = 0; i < enrollPlayerList.size(); i++) {
			 enrollPlayerService.save(enrollPlayerList.get(i));
		 }

		 return Result.ok("报名成功！");
	 }
	/**
	 * 添加
	 *
	 * @param enrollPlayer
	 * @return
	 */
	@ApiOperation(value="参赛球员-添加", notes="参赛球员-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody EnrollPlayer enrollPlayer) {
		enrollPlayerService.save(enrollPlayer);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param enrollPlayer
	 * @return
	 */
	@ApiOperation(value="参赛球员-编辑", notes="参赛球员-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody EnrollPlayer enrollPlayer) {
		enrollPlayerService.updateById(enrollPlayer);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="参赛球员-通过id删除", notes="参赛球员-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		enrollPlayerService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="参赛球员-批量删除", notes="参赛球员-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.enrollPlayerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="参赛球员-通过id查询", notes="参赛球员-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		EnrollPlayer enrollPlayer = enrollPlayerService.getById(id);
		return Result.ok(enrollPlayer);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param enrollPlayer
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, EnrollPlayer enrollPlayer) {
      return super.exportXls(request, enrollPlayer, EnrollPlayer.class, "参赛球员");
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
      return super.importExcel(request, response, EnrollPlayer.class);
  }

}
