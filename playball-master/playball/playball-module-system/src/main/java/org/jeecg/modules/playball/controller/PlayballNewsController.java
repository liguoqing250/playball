package org.jeecg.modules.playball.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.playball.entity.PlayballNews;
import org.jeecg.modules.playball.service.IPlayballNewsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 
 * @Author: 
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Slf4j
@Api(tags="资讯详情")
@RestController
@RequestMapping("/playball/playballNews")
public class PlayballNewsController extends JeecgController<PlayballNews, IPlayballNewsService> {
	@Autowired
	private IPlayballNewsService newsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param PlayballNews
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="资讯详情-分页列表查询", notes="资讯详情-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballNews playballNews,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		log.info("------------------------queryPageList");
		
		QueryWrapper<PlayballNews> queryWrapper = QueryGenerator.initQueryWrapper(playballNews, req.getParameterMap());
		Page<PlayballNews> page = new Page<PlayballNews>(pageNo, pageSize);
		IPage<PlayballNews> pageList = newsService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param PlayballNews
	 * @return
	 */
	@ApiOperation(value="资讯详情-添加", notes="资讯详情-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballNews playballNews) {
		newsService.save(playballNews);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param PlayballNews
	 * @return
	 */
	@ApiOperation(value="资讯详情-编辑", notes="资讯详情-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballNews playballNews) {
		newsService.updateById(playballNews);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="资讯详情-通过id删除", notes="资讯详情-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		newsService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="资讯详情-批量删除", notes="资讯详情-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.newsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="资讯详情-通过id查询", notes="资讯详情-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballNews playballNews = newsService.getById(id);
		return Result.ok(playballNews);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param PlayballNews
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballNews playballNews) {
      return super.exportXls(request, playballNews, PlayballNews.class, "资讯详情");
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
      return super.importExcel(request, response, PlayballNews.class);
  }

}
