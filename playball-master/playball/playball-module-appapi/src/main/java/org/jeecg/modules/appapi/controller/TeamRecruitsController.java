package org.jeecg.modules.appapi.controller;

import java.util.Arrays;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.TeamRecruits;
import org.jeecg.modules.appapi.service.TeamRecruitsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 招募信息
 * @Author: jeecg-boot
 * @Date:   2020-07-21
 * @Version: V1.0
 */
@Slf4j
@Api(tags="招募信息")
@RestController
@RequestMapping("/TeamRecruits")
public class TeamRecruitsController extends JeecgController<TeamRecruits, TeamRecruitsService> {
	@Autowired
	private TeamRecruitsService teamRecruitsService;

	 //分页获取球队信息
	 @PostMapping(value = "/list")
	 public Result<JSONObject> selectTeamByPage(@RequestBody Map<String,Object> params) {
		 Result<JSONObject> result = new Result<JSONObject>();
		 try{
			 System.out.println(params);
			 JSONObject obj = new JSONObject();
			 Page<TeamRecruits> page=teamRecruitsService.selectByPage(params);
			 obj.put("page",page);
			 result.setResult(obj);
			 result.success("请求成功");
		 }catch (Exception e){
			 e.printStackTrace();
			 result.error500("请求失败");
		 }
		 return result;
	 }

//	/**
//	 * 分页列表查询
//	 *
//	 * @param teamRecruits
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	@ApiOperation(value="招募信息-分页列表查询", notes="招募信息-分页列表查询")
//	@PostMapping(value = "/list")
//	public Result<?> queryPageList(TeamRecruits teamRecruits,
//								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//								   HttpServletRequest req) {
//		QueryWrapper<TeamRecruits> queryWrapper = QueryGenerator.initQueryWrapper(teamRecruits, req.getParameterMap());
//		Page<TeamRecruits> page = new Page<TeamRecruits>(pageNo, pageSize);
//		IPage<TeamRecruits> pageList = teamRecruitsService.page(page, queryWrapper);
//		return Result.ok(pageList);
//	}
	
	/**
	 * 添加
	 *
	 * @param teamRecruits
	 * @return
	 */
	@ApiOperation(value="招募信息-添加", notes="招募信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TeamRecruits teamRecruits) {
		teamRecruitsService.save(teamRecruits);
		return Result.ok("招募成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param teamRecruits
	 * @return
	 */
	@ApiOperation(value="招募信息-编辑", notes="招募信息-编辑")
	@PostMapping(value = "/edit")
	public Result<?> edit(@RequestBody TeamRecruits teamRecruits) {
		teamRecruitsService.updateById(teamRecruits);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="招募信息-通过id删除", notes="招募信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		teamRecruitsService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="招募信息-批量删除", notes="招募信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.teamRecruitsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@ApiOperation(value="招募信息-通过id查询", notes="招募信息-通过id查询")
	@PostMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TeamRecruits teamRecruits = teamRecruitsService.getById(id);
		return Result.ok(teamRecruits);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param teamRecruits
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, TeamRecruits teamRecruits) {
      return super.exportXls(request, teamRecruits, TeamRecruits.class, "招募信息");
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
      return super.importExcel(request, response, TeamRecruits.class);
  }

}
