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
import org.jeecg.modules.playball.entity.PlayballGame;
import org.jeecg.modules.playball.vo.PlayballGamesPage;
import org.jeecg.modules.playball.service.IPlayballGameService;
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
 * @Description: 赛事信息
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Slf4j
@Api(tags="赛事信息")
@RestController
@RequestMapping("/playball/playballGame")
public class PlayballGameController extends JeecgController<PlayballGame, IPlayballGameService> {
	@Autowired
	private IPlayballGameService playballGameService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballGame
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "赛事信息-分页列表查询")
	@ApiOperation(value="赛事信息-分页列表查询", notes="赛事信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballGame playballGame,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		/*QueryWrapper<PlayballGame> queryWrapper = QueryGenerator.initQueryWrapper(playballGame, req.getParameterMap());
		Page<PlayballGame> page = new Page<PlayballGame>(pageNo, pageSize);
		IPage<PlayballGame> pageList = playballGameService.page(page, queryWrapper);*/
		

		IPage<PlayballGamesPage> pageList = playballGameService.queryGameList(new Page(pageNo, pageSize));
		
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param playballGame
	 * @return
	 */
	@AutoLog(value = "赛事信息-添加")
	@ApiOperation(value="赛事信息-添加", notes="赛事信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballGame playballGame) {
		playballGameService.save(playballGame);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballGame
	 * @return
	 */
	@AutoLog(value = "赛事信息-编辑")
	@ApiOperation(value="赛事信息-编辑", notes="赛事信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballGame playballGame) {
		playballGameService.updateById(playballGame);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "赛事信息-通过id删除")
	@ApiOperation(value="赛事信息-通过id删除", notes="赛事信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		playballGameService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "赛事信息-批量删除")
	@ApiOperation(value="赛事信息-批量删除", notes="赛事信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.playballGameService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "赛事信息-通过id查询")
	@ApiOperation(value="赛事信息-通过id查询", notes="赛事信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballGame playballGame = playballGameService.getById(id);
		return Result.ok(playballGame);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballGame
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballGame playballGame) {
      return super.exportXls(request, playballGame, PlayballGame.class, "赛事信息");
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
      return super.importExcel(request, response, PlayballGame.class);
  }

}
