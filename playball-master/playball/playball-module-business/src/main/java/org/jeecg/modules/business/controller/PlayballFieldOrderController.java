package org.jeecg.modules.business.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.common.playball.entity.PlayballOrder;
import org.jeecg.common.playball.vo.PlayballOrderModel;
import org.jeecg.modules.business.service.IPlayballFieldOrderService;
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
 * @Description: 订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
@Slf4j
@Api(tags="订单管理")
@RestController
@RequestMapping("/business/playballOrder")
public class PlayballFieldOrderController extends JeecgController<PlayballOrder, IPlayballFieldOrderService> {
	@Autowired
	private IPlayballFieldOrderService playballOrderService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "订单管理-分页列表查询")
	@ApiOperation(value="订单管理-分页列表查询", notes="订单管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballOrderModel playballOrderModel,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		/*QueryWrapper<PlayballOrder> queryWrapper = QueryGenerator.initQueryWrapper(playballOrder, req.getParameterMap());
		Page<PlayballOrder> page = new Page<PlayballOrder>(pageNo, pageSize);
		IPage<PlayballOrder> pageList = playballOrderService.page(page, queryWrapper);*/
		//获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //获取用户id后获取用户属于的商家id
        String userId = sysUser.getId();
		
		IPage<PlayballOrderModel> pageList = playballOrderService.queryFieldOrderByUserId(new Page(pageNo, pageSize), playballOrderModel,userId);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param playballOrder
	 * @return
	 */
	@AutoLog(value = "订单管理-添加")
	@ApiOperation(value="订单管理-添加", notes="订单管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballOrder playballOrder) {
		playballOrderService.save(playballOrder);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballOrder
	 * @return
	 */
	@AutoLog(value = "订单管理-编辑")
	@ApiOperation(value="订单管理-编辑", notes="订单管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballOrder playballOrder) {
		playballOrderService.updateById(playballOrder);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单管理-通过id删除")
	@ApiOperation(value="订单管理-通过id删除", notes="订单管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		playballOrderService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "订单管理-批量删除")
	@ApiOperation(value="订单管理-批量删除", notes="订单管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.playballOrderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "订单管理-通过id查询")
	@ApiOperation(value="订单管理-通过id查询", notes="订单管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballOrder playballOrder = playballOrderService.getById(id);
		return Result.ok(playballOrder);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballOrder
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballOrder playballOrder) {
      return super.exportXls(request, playballOrder, PlayballOrder.class, "订单管理");
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
      return super.importExcel(request, response, PlayballOrder.class);
  }

}
