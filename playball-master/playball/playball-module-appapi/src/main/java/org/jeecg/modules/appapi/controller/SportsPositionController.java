package org.jeecg.modules.appapi.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.appapi.entity.SportsPosition;
import org.jeecg.modules.appapi.service.ISoortsPositionService;
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
 * @Description: 运动员位置
 * @Author: jeecg-boot
 * @Date:   2020-07-21
 * @Version: V1.0
 */
@Slf4j
@Api(tags="运动员位置")
@RestController
@RequestMapping("/SoortsPosition")
public class SportsPositionController extends JeecgController<SportsPosition, ISoortsPositionService> {
	@Autowired
	private ISoortsPositionService soortsPositionService;

	 /**
	  * 通过st_id查询
	  *
	  * @param stId
	  * @return
	  */
	 @ApiOperation(value="运动员位置-通过stId查询", notes="运动员位置-通过id查询")
	 @PostMapping(value = "/queryBystId")
	 public Result<?> queryByStId(@RequestParam(name="stId",required=true) Integer stId) {
		 List<SportsPosition> sportsPosition = soortsPositionService.queryByStId(stId);
		 return Result.ok(sportsPosition);
	 }
	/**
	 * 分页列表查询
	 *
	 * @param sportsPosition
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="运动员位置-分页列表查询", notes="运动员位置-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(SportsPosition sportsPosition,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<SportsPosition> queryWrapper = QueryGenerator.initQueryWrapper(sportsPosition, req.getParameterMap());
		Page<SportsPosition> page = new Page<SportsPosition>(pageNo, pageSize);
		IPage<SportsPosition> pageList = soortsPositionService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param sportsPosition
	 * @return
	 */
	@ApiOperation(value="运动员位置-添加", notes="运动员位置-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody SportsPosition sportsPosition) {
		soortsPositionService.save(sportsPosition);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param sportsPosition
	 * @return
	 */
	@ApiOperation(value="运动员位置-编辑", notes="运动员位置-编辑")
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody SportsPosition sportsPosition) {
		soortsPositionService.updateById(sportsPosition);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="运动员位置-通过id删除", notes="运动员位置-通过id删除")
	@PostMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		soortsPositionService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="运动员位置-批量删除", notes="运动员位置-批量删除")
	@PostMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.soortsPositionService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="运动员位置-通过id查询", notes="运动员位置-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		SportsPosition sportsPosition = soortsPositionService.getById(id);
		return Result.ok(sportsPosition);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param sportsPosition
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, SportsPosition sportsPosition) {
      return super.exportXls(request, sportsPosition, SportsPosition.class, "运动员位置");
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
      return super.importExcel(request, response, SportsPosition.class);
  }

}
