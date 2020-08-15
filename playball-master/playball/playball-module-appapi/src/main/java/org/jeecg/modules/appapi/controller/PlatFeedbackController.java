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
import org.jeecg.modules.appapi.entity.PlatFeedback;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.appapi.service.AppUsersService;
import org.jeecg.modules.appapi.service.IPlatFeedbackService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 意见反馈
 * @Author: jeecg-boot
 * @Date:   2020-08-15
 * @Version: V1.0
 */
@Slf4j
@Api(tags="意见反馈")
@RestController
@RequestMapping("/PlatFeedback")
public class PlatFeedbackController extends JeecgController<PlatFeedback, IPlatFeedbackService> {
	@Autowired
	private IPlatFeedbackService platFeedbackService;

	@Autowired
	private AppUsersService appUsersService;
	/**
	 * 分页列表查询
	 *
	 * @param platFeedback
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "意见反馈-分页列表查询")
	@ApiOperation(value="意见反馈-分页列表查询", notes="意见反馈-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlatFeedback platFeedback,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PlatFeedback> queryWrapper = QueryGenerator.initQueryWrapper(platFeedback, req.getParameterMap());
		Page<PlatFeedback> page = new Page<PlatFeedback>(pageNo, pageSize);
		IPage<PlatFeedback> pageList = platFeedbackService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param platFeedback
	 * @return
	 */
	@AutoLog(value = "意见反馈-添加")
	@ApiOperation(value="意见反馈-添加", notes="意见反馈-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlatFeedback platFeedback) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String token=request.getHeader("X-Access-Token");
		AppUsers appUsers=appUsersService.selectById(JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class).getU_id());
		platFeedback.setPfName(appUsers.getU_name());
		platFeedback.setPfPhone(appUsers.getU_phoneNumber());
		platFeedbackService.save(platFeedback);
		return Result.ok("问题已提交给相关工作人员，感谢您的反馈！");
	}
	
	/**
	 * 编辑
	 *
	 * @param platFeedback
	 * @return
	 */
	@AutoLog(value = "意见反馈-编辑")
	@ApiOperation(value="意见反馈-编辑", notes="意见反馈-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlatFeedback platFeedback) {
		platFeedbackService.updateById(platFeedback);
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
		platFeedbackService.removeById(id);
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
		this.platFeedbackService.removeByIds(Arrays.asList(ids.split(",")));
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
		PlatFeedback platFeedback = platFeedbackService.getById(id);
		return Result.ok(platFeedback);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param platFeedback
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlatFeedback platFeedback) {
      return super.exportXls(request, platFeedback, PlatFeedback.class, "意见反馈");
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
      return super.importExcel(request, response, PlatFeedback.class);
  }

}
