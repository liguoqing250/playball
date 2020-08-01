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
import org.jeecg.common.playball.entity.PlayballCMSNewsType;
import org.jeecg.modules.playball.service.IPlayballCMSNewsTypeService;
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
 * @Description: 资讯类型管理
 * @Author: jeecg-boot
 * @Date:   2020-08-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="资讯类型管理")
@RestController
@RequestMapping("/playball/playballCMSNewsType")
public class PlayballCMSNewsTypeController extends JeecgController<PlayballCMSNewsType, IPlayballCMSNewsTypeService> {
	@Autowired
	private IPlayballCMSNewsTypeService playballCMSNewsTypeService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballCMSNewsType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "资讯类型管理-分页列表查询")
	@ApiOperation(value="资讯类型管理-分页列表查询", notes="资讯类型管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballCMSNewsType playballCMSNewsType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PlayballCMSNewsType> queryWrapper = QueryGenerator.initQueryWrapper(playballCMSNewsType, req.getParameterMap());
		Page<PlayballCMSNewsType> page = new Page<PlayballCMSNewsType>(pageNo, pageSize);
		IPage<PlayballCMSNewsType> pageList = playballCMSNewsTypeService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param playballCMSNewsType
	 * @return
	 */
	@AutoLog(value = "资讯类型管理-添加")
	@ApiOperation(value="资讯类型管理-添加", notes="资讯类型管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballCMSNewsType playballCMSNewsType) {
		playballCMSNewsTypeService.save(playballCMSNewsType);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballCMSNewsType
	 * @return
	 */
	@AutoLog(value = "资讯类型管理-编辑")
	@ApiOperation(value="资讯类型管理-编辑", notes="资讯类型管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballCMSNewsType playballCMSNewsType) {
		playballCMSNewsTypeService.updateById(playballCMSNewsType);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "资讯类型管理-通过id删除")
	@ApiOperation(value="资讯类型管理-通过id删除", notes="资讯类型管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		playballCMSNewsTypeService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "资讯类型管理-批量删除")
	@ApiOperation(value="资讯类型管理-批量删除", notes="资讯类型管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.playballCMSNewsTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	@AutoLog(value = "资讯类型管理-查询所有")
	@ApiOperation(value="资讯类型管理-查询", notes="资讯类型管理-查询")
	@GetMapping(value = "/queryList")
	public Result<?> queryList() {
		List<PlayballCMSNewsType> list = playballCMSNewsTypeService.list();
		return Result.ok(list);
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "资讯类型管理-通过id查询")
	@ApiOperation(value="资讯类型管理-通过id查询", notes="资讯类型管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballCMSNewsType playballCMSNewsType = playballCMSNewsTypeService.getById(id);
		return Result.ok(playballCMSNewsType);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballCMSNewsType
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballCMSNewsType playballCMSNewsType) {
      return super.exportXls(request, playballCMSNewsType, PlayballCMSNewsType.class, "资讯类型管理");
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
      return super.importExcel(request, response, PlayballCMSNewsType.class);
  }

}
