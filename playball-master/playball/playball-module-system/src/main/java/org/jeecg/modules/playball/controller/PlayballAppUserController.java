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
import org.jeecg.common.playball.entity.PlayballAppUser;
import org.jeecg.modules.playball.service.IPlayballAppUserService;
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
 * @Description: 会员管理
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Slf4j
@Api(tags="会员管理")
@RestController
@RequestMapping("/playball/playballAppUser")
public class PlayballAppUserController extends JeecgController<PlayballAppUser, IPlayballAppUserService> {
	@Autowired
	private IPlayballAppUserService playballAppUserService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballAppUser
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "会员管理-分页列表查询")
	@ApiOperation(value="会员管理-分页列表查询", notes="会员管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballAppUser playballAppUser,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		//数据库字段定义没有按框架规定的字段配置，所以暂不能用此方法
		/*QueryWrapper<PlayballAppUser> queryWrapper = QueryGenerator.initQueryWrapper(playballAppUser, req.getParameterMap());
		Page<PlayballAppUser> page = new Page<PlayballAppUser>(pageNo, pageSize);
		IPage<PlayballAppUser> pageList = playballAppUserService.page(page, queryWrapper);*/
		IPage<PlayballAppUser> pageList = playballAppUserService.quereyList(new Page(pageNo,pageSize), playballAppUser);
		log.info("----------------"+pageList);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param playballAppUser
	 * @return
	 */
	@AutoLog(value = "会员管理-添加")
	@ApiOperation(value="会员管理-添加", notes="会员管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballAppUser playballAppUser) {
		playballAppUserService.save(playballAppUser);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballAppUser
	 * @return
	 */
	@AutoLog(value = "会员管理-编辑")
	@ApiOperation(value="会员管理-编辑", notes="会员管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballAppUser playballAppUser) {
		playballAppUserService.updateById(playballAppUser);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会员管理-通过id删除")
	@ApiOperation(value="会员管理-通过id删除", notes="会员管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		playballAppUserService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "会员管理-批量删除")
	@ApiOperation(value="会员管理-批量删除", notes="会员管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.playballAppUserService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会员管理-通过id查询")
	@ApiOperation(value="会员管理-通过id查询", notes="会员管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballAppUser playballAppUser = playballAppUserService.getById(id);
		return Result.ok(playballAppUser);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballAppUser
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballAppUser playballAppUser) {
      return super.exportXls(request, playballAppUser, PlayballAppUser.class, "会员管理");
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
      return super.importExcel(request, response, PlayballAppUser.class);
  }

}
