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
import org.jeecg.common.playball.entity.PlayballAbilityValue;
import org.jeecg.common.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.entity.PlayballGame;
import org.jeecg.modules.playball.service.IPlayballAbilityValueService;
import org.jeecg.modules.playball.vo.PlayballScheduleResultVo;

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
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 能力值
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Slf4j
@Api(tags="能力值")
@RestController
@RequestMapping("/playball/playballAbilityValue")
public class PlayballAbilityValueController extends JeecgController<PlayballAbilityValue, IPlayballAbilityValueService> {
	@Autowired
	private IPlayballAbilityValueService playballAbilityValueService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballAbilityValue
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "能力值-分页列表查询")
	@ApiOperation(value="能力值-分页列表查询", notes="能力值-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballAbilityValue playballAbilityValue,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PlayballAbilityValue> queryWrapper = QueryGenerator.initQueryWrapper(playballAbilityValue, req.getParameterMap());
		Page<PlayballAbilityValue> page = new Page<PlayballAbilityValue>(pageNo, pageSize);
		IPage<PlayballAbilityValue> pageList = playballAbilityValueService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	@GetMapping(value="/listBySportsId")
	public Result<?> getListBySportsId(HttpServletRequest req) {
		Integer sportsId = Integer.valueOf(req.getParameter("sportsId"));
		
		Result<List<PlayballAbilityValue>> result = new Result<List<PlayballAbilityValue>>();
		try {
			List<PlayballAbilityValue> list = playballAbilityValueService.getListBySportsId(sportsId);
			result.setSuccess(true);
			result.setMessage("查找成功");
        	result.setResult(list);
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.setSuccess(false);
			result.setMessage("查找过程中出现了异常: " + e.getMessage());
			return result;
		}
	}
	
	/**
	 * 添加
	 *
	 * @param playballAbilityValue
	 * @return
	 */
	@AutoLog(value = "能力值-添加")
	@ApiOperation(value="能力值-添加", notes="能力值-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballAbilityValue playballAbilityValue) {
		playballAbilityValueService.save(playballAbilityValue);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballAbilityValue
	 * @return
	 */
	@AutoLog(value = "能力值-编辑")
	@ApiOperation(value="能力值-编辑", notes="能力值-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballAbilityValue playballAbilityValue) {
		playballAbilityValueService.updateById(playballAbilityValue);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "能力值-通过id删除")
	@ApiOperation(value="能力值-通过id删除", notes="能力值-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		playballAbilityValueService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "能力值-批量删除")
	@ApiOperation(value="能力值-批量删除", notes="能力值-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.playballAbilityValueService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "能力值-通过id查询")
	@ApiOperation(value="能力值-通过id查询", notes="能力值-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballAbilityValue playballAbilityValue = playballAbilityValueService.getById(id);
		return Result.ok(playballAbilityValue);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballAbilityValue
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballAbilityValue playballAbilityValue) {
      return super.exportXls(request, playballAbilityValue, PlayballAbilityValue.class, "能力值");
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
      return super.importExcel(request, response, PlayballAbilityValue.class);
  }

}
