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
import org.jeecg.modules.appapi.entity.SportsType;
import org.jeecg.modules.appapi.service.ISportsTypeService;
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
 * @Description: 运动类型表
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="运动类型表")
@RestController
@RequestMapping("/sportsType")
public class SportsTypeController extends JeecgController<SportsType, ISportsTypeService> {
	@Autowired
	private ISportsTypeService sportsTypeService;

	 /**
	  * 查询所有
	  *
	  * @param
	  * @return
	  */
	 @ApiOperation(value="运动类型表-添加", notes="运动类型表-添加")
	 @PostMapping(value = "/getSportsType")
	 public Result<JSONObject> getSportsType() {
		 Result<JSONObject> result = new Result<JSONObject>();
		 JSONObject obj = new JSONObject();
		 obj.put("data",sportsTypeService.getSportsType());
		 result.setResult(obj);
		 return result;
	 }
	 /**
	  * 查询所有
	  *
	  * @param
	  * @return
	  */
	 @ApiOperation(value="运动类型表-添加", notes="运动类型表-添加")
	 @PostMapping(value = "/querySportsTypeByBusinessId")
	 public Result<JSONObject> querySportsTypeByBusinessId(String id) {
		 Result<JSONObject> result = new Result<JSONObject>();
		 JSONObject obj = new JSONObject();
		 obj.put("data",sportsTypeService.querySportsTypeByBusinessId(id));
		 result.setResult(obj);
		 return result;
	 }
	/**
	 * 添加
	 *
	 * @param sportsType
	 * @return
	 */
	@ApiOperation(value="运动类型表-添加", notes="运动类型表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SportsType sportsType) {
		sportsTypeService.save(sportsType);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param sportsType
	 * @return
	 */
	@ApiOperation(value="运动类型表-编辑", notes="运动类型表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody SportsType sportsType) {
		sportsTypeService.updateById(sportsType);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="运动类型表-通过id删除", notes="运动类型表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		sportsTypeService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="运动类型表-批量删除", notes="运动类型表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.sportsTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="运动类型表-通过id查询", notes="运动类型表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SportsType sportsType = sportsTypeService.getById(id);
		return Result.ok(sportsType);
	}



}
