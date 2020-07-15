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
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.FieldBookable;
import org.jeecg.modules.appapi.entity.FieldReserveInfo;
import org.jeecg.modules.appapi.entity.Game;
import org.jeecg.modules.appapi.service.IFieldReserveInfoService;
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
 * @Description: 场地预定
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="场地预定")
@RestController
@RequestMapping("/FieldReserveInfo")
public class FieldReserveInfoController extends JeecgController<FieldReserveInfo, IFieldReserveInfoService> {
	@Autowired
	private IFieldReserveInfoService fieldReserveInfoService;

	 @Autowired
	 private HttpServletRequest request;
	 /**
	  * 查询已预订信息
	  *
	  * @param bid,reserveTime
	  * @return
	  */
	 @ApiOperation(value="查询已预订信息", notes="查询已预订信息")
	 @PostMapping(value = "/queryFieldReserveInfo")
	 public Result<?> queryFieldReserveInfo(@RequestParam(name="bid",required=true) String bid,@RequestParam(name="reserveTime",required=true) String reserveTime,@RequestParam(name="stId",required=true)Integer stId) {
		 List<FieldReserveInfo> list = fieldReserveInfoService.queryFieldReserveInfo(bid,reserveTime,stId);
		 return Result.ok(list);
	 }

	 /**
	  * 查询可预订最早时间
	  *
	  * @param fieldReserveInfo
	  * @return
	  */
	 @ApiOperation(value="运动类型，所属场地", notes="查询可预订最早时间")
	 @PostMapping(value = "/queryFieldBookable")
	 public Result<?> queryFieldBookable(FieldReserveInfo fieldReserveInfo) {
		 List<FieldBookable> list = fieldReserveInfoService.queryFieldBookable(fieldReserveInfo);
		 return Result.ok(list);
	 }
	/**
	 * 分页列表查询
	 *
	 * @param FieldReserveInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="场地预定-分页列表查询", notes="场地预定-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(FieldReserveInfo FieldReserveInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<FieldReserveInfo> queryWrapper = QueryGenerator.initQueryWrapper(FieldReserveInfo, req.getParameterMap());
		Page<FieldReserveInfo> page = new Page<FieldReserveInfo>(pageNo, pageSize);
		IPage<FieldReserveInfo> pageList = fieldReserveInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param FieldReserveInfo
	 * @return
	 */
	@ApiOperation(value="场地预定-添加", notes="场地预定-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FieldReserveInfo FieldReserveInfo) {
		String token=request.getHeader("X-Access-Token");
		AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
		FieldReserveInfo.setUserId(appUsers.getU_id());
		fieldReserveInfoService.save(FieldReserveInfo);
		return Result.ok(FieldReserveInfo);
	}
	
	/**
	 * 编辑
	 *
	 * @param FieldReserveInfo
	 * @return
	 */
	@ApiOperation(value="场地预定-编辑", notes="场地预定-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FieldReserveInfo FieldReserveInfo) {
		fieldReserveInfoService.updateById(FieldReserveInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="场地预定-通过id删除", notes="场地预定-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fieldReserveInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="场地预定-批量删除", notes="场地预定-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fieldReserveInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="场地预定-通过id查询", notes="场地预定-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FieldReserveInfo FieldReserveInfo = fieldReserveInfoService.getById(id);
		return Result.ok(FieldReserveInfo);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param FieldReserveInfo
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, FieldReserveInfo FieldReserveInfo) {
      return super.exportXls(request, FieldReserveInfo, FieldReserveInfo.class, "场地预定");
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
      return super.importExcel(request, response, FieldReserveInfo.class);
  }

}
