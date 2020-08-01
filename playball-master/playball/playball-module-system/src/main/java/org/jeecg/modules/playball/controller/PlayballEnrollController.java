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
import org.jeecg.modules.playball.entity.PlayballEnroll;
import org.jeecg.common.playball.entity.PlayballTeam;
import org.jeecg.common.playball.vo.PlayballPlayerModel;
import org.jeecg.modules.playball.service.IPlayballEnrollService;
import org.jeecg.modules.playball.vo.PlayballEnrollPage;

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
 * @Description: 报名球队管理
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
@Slf4j
@Api(tags="报名球队管理")
@RestController
@RequestMapping("/playball/playballEnroll")
public class PlayballEnrollController extends JeecgController<PlayballEnroll, IPlayballEnrollService> {
	@Autowired
	private IPlayballEnrollService playballEnrollService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballEnroll
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "报名球队管理-分页列表查询")
	@ApiOperation(value="报名球队管理-分页列表查询", notes="报名球队管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballEnroll playballEnroll,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		/*QueryWrapper<PlayballEnroll> queryWrapper = QueryGenerator.initQueryWrapper(playballEnroll, req.getParameterMap());
		Page<PlayballEnroll> page = new Page<PlayballEnroll>(pageNo, pageSize);
		IPage<PlayballEnroll> pageList = playballEnrollService.page(page, queryWrapper);*/
		PlayballEnrollPage enrollPage = new PlayballEnrollPage();
		enrollPage.setGamesName(req.getParameter("gamesName"));
		enrollPage.setTeamName(req.getParameter("teamName"));
		
		IPage<PlayballEnrollPage> pageList = playballEnrollService.queryEnrollList(new Page(pageNo, pageSize), enrollPage);
		
		return Result.ok(pageList);
	}
	
	//通过赛事id获取报名的队伍
	 @GetMapping(value = "/listByGameId")
	 public Result<?> getEnrollTeamByGamesId(HttpServletRequest req) {
		 Integer gamesId = Integer.valueOf(req.getParameter("gamesId"));
		 Result<List<PlayballTeam>> result = new Result<>();
		 try {
			 List<PlayballTeam> enrollTeamList = playballEnrollService.getEnrollTeamByGamesId(gamesId);
			 log.info("获取enrollTeamList="+enrollTeamList);
			 if (enrollTeamList != null && enrollTeamList.size() > 0) {
		          result.setSuccess(true);
		          result.setMessage("查找成功");
		          result.setResult(enrollTeamList);
		      } else {
		          result.setSuccess(false);
		          result.setMessage("查找失败");
		      }
		      return result;
		 	} catch (Exception e) {
		  	log.error(e.getMessage(), e);
		      result.setSuccess(false);
		      result.setMessage("查找过程中出现了异常: " + e.getMessage());
		      return result;
		 	}
	 }
	 
	 @GetMapping(value = "/getEnrollPlayerById")
	 public Result<?> getEnrollPlayerById(PlayballEnroll playballEnroll, HttpServletRequest req) {
		 Result<List<PlayballPlayerModel>> result = new Result<>();
		 try {
			 List<PlayballPlayerModel> list = playballEnrollService.getEnrollTeamPlayerById(playballEnroll.getId());
			 
			 result.setResult(list);
			 result.setSuccess(true);
		     result.setMessage("查找成功");
		     return result;
		     
		 }catch(Exception e) {
			 result.setSuccess(false);
		     result.setMessage("查找过程中出现了异常: " + e.getMessage());
		     return result;
		 }
	 }
	
	/**
	 * 添加
	 *
	 * @param playballEnroll
	 * @return
	 */
	@AutoLog(value = "报名球队管理-添加")
	@ApiOperation(value="报名球队管理-添加", notes="报名球队管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballEnroll playballEnroll) {
		playballEnrollService.save(playballEnroll);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballEnroll
	 * @return
	 */
	@AutoLog(value = "报名球队管理-编辑")
	@ApiOperation(value="报名球队管理-编辑", notes="报名球队管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballEnroll playballEnroll) {
		playballEnrollService.updateById(playballEnroll);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "报名球队管理-通过id删除")
	@ApiOperation(value="报名球队管理-通过id删除", notes="报名球队管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		playballEnrollService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "报名球队管理-批量删除")
	@ApiOperation(value="报名球队管理-批量删除", notes="报名球队管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.playballEnrollService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "报名球队管理-通过id查询")
	@ApiOperation(value="报名球队管理-通过id查询", notes="报名球队管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballEnroll playballEnroll = playballEnrollService.getById(id);
		return Result.ok(playballEnroll);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballEnroll
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballEnroll playballEnroll) {
      return super.exportXls(request, playballEnroll, PlayballEnroll.class, "报名球队管理");
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
      return super.importExcel(request, response, PlayballEnroll.class);
  }

}
