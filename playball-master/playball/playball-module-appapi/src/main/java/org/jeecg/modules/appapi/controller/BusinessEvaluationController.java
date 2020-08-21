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
import org.jeecg.modules.appapi.entity.Arena;
import org.jeecg.modules.appapi.entity.BusinessEvaluation;
import org.jeecg.modules.appapi.entity.bo.PlayballUserEvaluationBo;
import org.jeecg.modules.appapi.mapper.ArenaMapper;
import org.jeecg.modules.appapi.service.IBusinessEvaluationService;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 商家评价
 * @Author: jeecg-boot
 * @Date:   2020-08-06
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商家评价")
@RestController
@RequestMapping("/BusinessEvaluation")
public class BusinessEvaluationController extends JeecgController<BusinessEvaluation, IBusinessEvaluationService> {
	@Autowired
	private IBusinessEvaluationService businessEvaluationService;
	@Autowired
	 ArenaMapper arenaMapper;
	 /**
	  * 查询商家评论
	  *
	  * @param bid
	  * @return
	  */
	 @ApiOperation(value="商家评价-查询商家评论", notes="商家评价-查询商家评论")
	 @PostMapping(value = "/QueryBusinessEvaluation")
	 public Result<?> QueryBusinessEvaluation(String bid) {
		 return Result.ok( businessEvaluationService.QueryBusinessEvaluation(bid));
	 }
	 /**
	  * 查询评论
	  *
	  * @param ordId
	  * @return
	  */
	 @ApiOperation(value="商家评价-查询商家评论", notes="商家评价-查询商家评论")
	 @PostMapping(value = "/QueryBusinessEvaluationByOrdId")
	 public Result<?> QueryBusinessEvaluationByOrdId(String ordId) {
		 return Result.ok( businessEvaluationService.QueryBusinessEvaluationByOrdId(ordId));
	 }
	/**
	 * 添加
	 *
	 * @param businessEvaluation
	 * @return
	 */
	@ApiOperation(value="商家评价-添加", notes="商家评价-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BusinessEvaluation businessEvaluation) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String token=request.getHeader("X-Access-Token");
		AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
		businessEvaluation.setUid(appUsers.getU_id());
		businessEvaluationService.save(businessEvaluation);
		Arena arena=new Arena();
		arena.setId(businessEvaluation.getBid());
		String value=businessEvaluationService.QueryBusinessEvaluationScore(businessEvaluation.getBid());

		arena.setAvgscore(value);
		arenaMapper.updateById(arena);
		return Result.ok("评价成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param businessEvaluation
	 * @return
	 */
	@ApiOperation(value="商家评价-编辑", notes="商家评价-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BusinessEvaluation businessEvaluation) {
		businessEvaluationService.updateById(businessEvaluation);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="商家评价-通过id删除", notes="商家评价-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		businessEvaluationService.removeById(id);
		return Result.ok("删除成功!");
	}
	

	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="商家评价-通过id查询", notes="商家评价-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BusinessEvaluation businessEvaluation = businessEvaluationService.getById(id);
		return Result.ok(businessEvaluation);
	}



}
