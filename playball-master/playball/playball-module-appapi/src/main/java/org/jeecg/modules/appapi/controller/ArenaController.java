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
import org.jeecg.modules.appapi.entity.Arena;
import org.jeecg.modules.appapi.service.IArenaService;
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
 * @Description: 商家表
 * @Author: jeecg-boot
 * @Date:   2020-07-09
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商家表")
@RestController
@RequestMapping("/Arena")
public class ArenaController extends JeecgController<Arena, IArenaService> {
	@Autowired
	private IArenaService arenaService;
	
	/**
	 * 分页列表查询
	 *
	 * @param arena
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="商家表-分页列表查询", notes="商家表-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(Arena arena,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Arena> queryWrapper = QueryGenerator.initQueryWrapper(arena, req.getParameterMap());
		Page<Arena> page = new Page<Arena>(pageNo, pageSize);
		IPage<Arena> pageList = arenaService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param arena
	 * @return
	 */
	
	@ApiOperation(value="商家表-添加", notes="商家表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Arena arena) {
		arenaService.save(arena);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param arena
	 * @return
	 */
	
	@ApiOperation(value="商家表-编辑", notes="商家表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Arena arena) {
		arenaService.updateById(arena);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="商家表-通过id删除", notes="商家表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		arenaService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="商家表-批量删除", notes="商家表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.arenaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="商家表-通过id查询", notes="商家表-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(String id) {
		System.out.println(id);
		Arena arena = arenaService.getById(id);
		return Result.ok(arena);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param arena
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, Arena arena) {
      return super.exportXls(request, arena, Arena.class, "商家表");
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
      return super.importExcel(request, response, Arena.class);
  }

}
