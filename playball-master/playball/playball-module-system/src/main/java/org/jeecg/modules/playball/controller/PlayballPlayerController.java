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
import org.jeecg.common.playball.entity.PlayballPlayer;
import org.jeecg.common.playball.entity.PlayballSportsData;
import org.jeecg.common.playball.vo.PlayballPlayerModel;
import org.jeecg.modules.playball.service.IPlayballPlayerService;
import org.jeecg.modules.playball.service.IPlayballScheduleUserDataService;
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
 * @Description: 队员管理
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Slf4j
@Api(tags="队员管理")
@RestController
@RequestMapping("/playball/playballPlayer")
public class PlayballPlayerController extends JeecgController<PlayballPlayer, IPlayballPlayerService> {
	@Autowired
	private IPlayballPlayerService playballPlayerService;
	
	@Autowired
	private IPlayballScheduleUserDataService dataService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballPlayer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "队员管理-分页列表查询")
	@ApiOperation(value="队员管理-分页列表查询", notes="队员管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballPlayerModel playerModel,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		/*QueryWrapper<PlayballPlayer> queryWrapper = QueryGenerator.initQueryWrapper(playballPlayer, req.getParameterMap());
		Page<PlayballPlayer> page = new Page<PlayballPlayer>(pageNo, pageSize);
		IPage<PlayballPlayer> pageList = playballPlayerService.page(page, queryWrapper);*/
		IPage<PlayballPlayerModel> pageList = playballPlayerService.queryList(new Page(pageNo,pageSize), playerModel);
		return Result.ok(pageList);
	}
	
	@GetMapping(value="/getGamePlayersList")
	public Result<?> getListBySportsId(HttpServletRequest req) {
		Result<List<PlayballPlayerModel>> result = new Result<List<PlayballPlayerModel>>();
		try {
			List<PlayballPlayerModel> list = playballPlayerService.getGamePlayersList(req.getParameter("teamId"),req.getParameter("gameId"),req.getParameter("scheduleId"));
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
	 * @param playballPlayer
	 * @return
	 */
	@AutoLog(value = "队员管理-添加")
	@ApiOperation(value="队员管理-添加", notes="队员管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballPlayer playballPlayer) {
		playballPlayerService.save(playballPlayer);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballPlayer
	 * @return
	 */
	@AutoLog(value = "队员管理-编辑")
	@ApiOperation(value="队员管理-编辑", notes="队员管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballPlayer playballPlayer) {
		playballPlayerService.updateById(playballPlayer);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "队员管理-通过id删除")
	@ApiOperation(value="队员管理-通过id删除", notes="队员管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		playballPlayerService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "队员管理-批量删除")
	@ApiOperation(value="队员管理-批量删除", notes="队员管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.playballPlayerService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "队员管理-通过id查询")
	@ApiOperation(value="队员管理-通过id查询", notes="队员管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballPlayer playballPlayer = playballPlayerService.getById(id);
		return Result.ok(playballPlayer);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballPlayer
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballPlayer playballPlayer) {
      return super.exportXls(request, playballPlayer, PlayballPlayer.class, "队员管理");
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
      return super.importExcel(request, response, PlayballPlayer.class);
  }

}
