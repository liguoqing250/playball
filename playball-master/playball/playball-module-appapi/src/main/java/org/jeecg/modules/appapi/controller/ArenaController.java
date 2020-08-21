package org.jeecg.modules.appapi.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.appapi.entity.Arena;
import org.jeecg.modules.appapi.entity.bo.ArenaBo;
import org.jeecg.modules.appapi.entity.vo.ArenaVo;
import org.jeecg.modules.appapi.mapper.ArenaMapper;
import org.jeecg.modules.appapi.service.IArenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: 商家表
 * @Author: jeecg-boot
 * @Date:   2020-07-09
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商家表")
@RestController
@RequestMapping("/Arena")
public class ArenaController extends JeecgController<Arena, IArenaService> {
	@Autowired
	private IArenaService arenaService;
	
	@Autowired
	private ArenaMapper mapper;

	/**
	 * 分页列表查询
	 *
	 * @param arena
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@ApiOperation(value="商家表-分页列表查询", notes="商家表-分页列表查询")
	@PostMapping(value = "/list")
	public Result<?> queryPageList(Arena arena,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {

		Page<Arena> page = new Page<Arena>(pageNo, pageSize);
		IPage<Arena> pageList = arenaService.queryPageList(page,req.getParameterMap());
		return Result.ok(pageList);
	}
//	/**
//	 * 分页列表查询
//	 *
//	 * @param arena
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@ApiOperation(value="商家表-分页列表查询", notes="商家表-分页列表查询")
//	@PostMapping(value = "/list")
//	public Result<?> queryPageList(Arena arena,
//								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//								   HttpServletRequest req) {
//		QueryWrapper<Arena> queryWrapper = QueryGenerator.initQueryWrapper(arena, req.getParameterMap());
//		Page<Arena> page = new Page<Arena>(pageNo, pageSize);
//		IPage<Arena> pageList = arenaService.page(page, queryWrapper);
//		return Result.ok(pageList);
//	}
	
	/**
	 * 添加
	 *
	 * @param arena
	 * @return
	 */
	
	@ApiOperation(value="商家表-添加", notes="商家表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Arena arena) {
		arenaService.save(arena);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param arena
	 * @return
	 */
	
	@ApiOperation(value="商家表-编辑", notes="商家表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Arena arena) {
		arenaService.updateById(arena);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="商家表-通过id删除", notes="商家表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		arenaService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="商家表-批量删除", notes="商家表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.arenaService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="商家表-通过id查询", notes="商家表-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(String id) {
		System.out.println(id);
		Arena arena = arenaService.getById(id);
		return Result.ok(arena);
	}
	/**
	 * 通过id查询
	 *
	 * @param ordId
	 * @return
	 */
	@ApiOperation(value="商家表-通过ordId查询", notes="商家表-通过ordId查询")
	@PostMapping(value = "/queryByOrdId")
	public Result<?> queryByOrdId(String ordId) {
		Arena arena = arenaService.queryByOrdId(ordId);
		return Result.ok(arena);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param arena
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, Arena arena) {
      return super.exportXls(request, arena, Arena.class, "商家表");
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
      return super.importExcel(request, response, Arena.class);
  }
  
  	//全文搜索查询商家球馆
	@GetMapping("/findFulltextNews")
	public Result<?> findFulltextDynamic(ArenaVo arenaVo){
		Page<Arena> page = new Page<Arena>(arenaVo.getPage(), arenaVo.getLimit());
		IPage<ArenaBo> fieldFulltext = mapper.findFieldFulltext(page, arenaVo);
		return Result.ok(fieldFulltext);
	}

}
