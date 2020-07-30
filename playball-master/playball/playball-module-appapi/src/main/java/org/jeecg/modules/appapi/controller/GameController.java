package org.jeecg.modules.appapi.controller;

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
import org.jeecg.modules. appapi.entity.Game;
import org.jeecg.modules.appapi.mapper.GameMapper;
import org.jeecg.modules. appapi.service.IGameService;
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
 * @Description: 比赛
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="比赛")
@RestController
@RequestMapping("/Game")
public class GameController extends JeecgController<Game, IGameService> {
	@Autowired
	private IGameService gameService;
	
	@Autowired
	private GameMapper mapper;


	/**
	 * 分页列表查询
	 *
	 * @param game
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="比赛-分页列表查询", notes="比赛-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(Game game,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Game> queryWrapper = QueryGenerator.initQueryWrapper(game, req.getParameterMap());
		Page<Game> page = new Page<Game>(pageNo, pageSize);
		IPage<Game> pageList = gameService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param game
	 * @return
	 */
	@ApiOperation(value="比赛-添加", notes="比赛-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Game game) {
		gameService.save(game);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param game
	 * @return
	 */
	@ApiOperation(value="比赛-编辑", notes="比赛-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Game game) {
		gameService.updateById(game);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="比赛-通过id删除", notes="比赛-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		gameService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="比赛-批量删除", notes="比赛-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.gameService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	 /**
	  * 通过商家id查询
	  *
	  * @param id
	  * @return
	  */
	 @ApiOperation(value="比赛-通过id查询", notes="比赛-通过id查询")
	 @PostMapping(value = "/queryByBid")
	 public Result<?> queryByBid(@RequestParam(name="id",required=true) String id) {
		 List<Game> game = gameService.queryByBid(id);
		 return Result.ok(game);
	 }
	 /**
	  * 通过场地id查询
	  *
	  * @param id
	  * @return
	  */
	 @ApiOperation(value="比赛-通过id查询", notes="比赛-通过id查询")
	 @PostMapping(value = "/queryByFieldId")
	 public Result<?> queryByFieldId(@RequestParam(name="id",required=true) Integer id) {
		 List<Game> game = gameService.queryByFieldId(id);
		 return Result.ok(game);
	 }

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="比赛-通过id查询", notes="比赛-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Game game = gameService.getById(id);
		return Result.ok(game);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param game
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, Game game) {
      return super.exportXls(request, game, Game.class, "比赛");
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
      return super.importExcel(request, response, Game.class);
  }
  
  @GetMapping("/newestGame")
  public Result<?> newestGame() {
	  //默认分页 5
	  Page<Game> page = new Page<Game>(1, 5);
	  //时间倒序
	  QueryWrapper<Game> queryWrapper = new QueryWrapper<Game>();
	  	queryWrapper.orderByDesc("start_time");
	  IPage<Game> selectPage = mapper.selectPage(page, queryWrapper);
      return Result.ok(selectPage);
  }

}
