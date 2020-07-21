package org.jeecg.modules.appapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.commons.collections.map.HashedMap;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.appapi.entity.FieldInfo;
import org.jeecg.modules.appapi.service.IFieldInfoService;
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
 * @Description: 场馆详情
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="场馆详情")
@RestController
@RequestMapping("/FieldInfo")
public class FieldInfoController extends JeecgController<FieldInfo, IFieldInfoService> {
	@Autowired
	private IFieldInfoService fieldInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param fieldInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="场馆详情-分页列表查询", notes="场馆详情-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(FieldInfo fieldInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<FieldInfo> queryWrapper = QueryGenerator.initQueryWrapper(fieldInfo, req.getParameterMap());
		Page<FieldInfo> page = new Page<FieldInfo>(pageNo, pageSize);
		IPage<FieldInfo> pageList = fieldInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param fieldInfo
	 * @return
	 */
	@ApiOperation(value="场馆详情-添加", notes="场馆详情-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FieldInfo fieldInfo) {
		fieldInfoService.save(fieldInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param fieldInfo
	 * @return
	 */
	@ApiOperation(value="场馆详情-编辑", notes="场馆详情-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FieldInfo fieldInfo) {
		fieldInfoService.updateById(fieldInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="场馆详情-通过id删除", notes="场馆详情-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fieldInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="场馆详情-批量删除", notes="场馆详情-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fieldInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	 * 通过用户订单查询
	 *
	 *
	 * @return
	 */
	@ApiOperation(value="场馆详情-通过用户订单查询", notes="场馆详情-通过用户订单查询")
	@PostMapping(value = "/queryByMyOrders")
	public Result<?> queryByMyOrders() {
		List<FieldInfo> fieldInfo = fieldInfoService.queryByMyOrders();
		return Result.ok(fieldInfo);
	}
	 /**
	  * 通过id查询
	  *
	  * @param id
	  * @return
	  */
	 @ApiOperation(value="场馆详情-通过id查询", notes="场馆详情-通过id查询")
	 @PostMapping(value = "/queryById")
	 public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		 FieldInfo fieldInfo = fieldInfoService.getById(id);
		 return Result.ok(fieldInfo);
	 }
	 /**
	  * 通过商家id查询
	  *
	  * @param bid
	  * @return
	  */
	 @ApiOperation(value="场馆详情-通过id查询", notes="场馆详情-通过id查询")
	 @PostMapping(value = "/queryBybId")
	 public Result<?> queryBybId(@RequestParam(name="bid",required=true) String bid,@RequestParam(name="stId",required=true) Integer stId) {
		 Map<String,Object> map=new HashedMap();
		 	map.put("business_id",bid);
		 	map.put("sports_id",stId);
		 List<FieldInfo> fieldInfo = fieldInfoService.listMaps(map);
		 return Result.ok(fieldInfo);
	 }
  /**
   * 导出excel
   *
   * @param request
   * @param fieldInfo
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, FieldInfo fieldInfo) {
      return super.exportXls(request, fieldInfo, FieldInfo.class, "场馆详情");
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
      return super.importExcel(request, response, FieldInfo.class);
  }

}
