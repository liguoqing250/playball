package org.jeecg.modules.business.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.common.playball.entity.PlayballFieldInfo;
import org.jeecg.common.playball.vo.PlayballFieldInfoModel;
import org.jeecg.modules.business.service.IPlayballFieldInfoService;
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
 * @Description: 场地信息
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
@Slf4j
@Api(tags="场地信息")
@RestController
@RequestMapping("/business/playballFieldInfo")
public class PlayballFieldInfoController extends JeecgController<PlayballFieldInfo, IPlayballFieldInfoService> {
	@Autowired
	private IPlayballFieldInfoService playballFieldInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param playballFieldInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "场地信息-分页列表查询")
	@ApiOperation(value="场地信息-分页列表查询", notes="场地信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PlayballFieldInfoModel playballFieldInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		/*QueryWrapper<PlayballFieldInfo> queryWrapper = QueryGenerator.initQueryWrapper(playballFieldInfo, req.getParameterMap());
		Page<PlayballFieldInfo> page = new Page<PlayballFieldInfo>(pageNo, pageSize);
		IPage<PlayballFieldInfo> pageList = playballFieldInfoService.page(page, queryWrapper);*/
		//获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //获取用户id后获取用户属于的商家id
        String userId = sysUser.getId();
        
    	IPage<PlayballFieldInfoModel> pageList = playballFieldInfoService.queryFieldInfoByUserId(new Page(pageNo, pageSize),playballFieldInfo,userId);
    	
		return Result.ok(pageList);
	}
	
	 /**
     *
     * @param 获取场馆信息的分页列表通过运动类型
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/listBySportsId")
    public Result<?> queryPageListBySports(HttpServletRequest req) {
    	
    	Integer sportsId = Integer.valueOf(req.getParameter("sportsId"));
    	log.info("获取sportsid="+req.getParameter("sportsId"));
    			
    	List<PlayballFieldInfoModel> list = playballFieldInfoService.getFieldInfoBySportsId(sportsId);
    	
        return Result.ok(list);
    }
    
	/**
	 * 添加
	 *
	 * @param playballFieldInfo
	 * @return
	 */
	@AutoLog(value = "场地信息-添加")
	@ApiOperation(value="场地信息-添加", notes="场地信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PlayballFieldInfo playballFieldInfo) {
		//获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //获取用户id后获取用户属于的商家id
        String userId = sysUser.getId();
        String businessId = playballFieldInfoService.getBusinessIdByUserId(userId);
        log.info("商家的id="+businessId);
        playballFieldInfo.setBusinessId(businessId);
    	
		playballFieldInfoService.save(playballFieldInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param playballFieldInfo
	 * @return
	 */
	@AutoLog(value = "场地信息-编辑")
	@ApiOperation(value="场地信息-编辑", notes="场地信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PlayballFieldInfo playballFieldInfo) {
		playballFieldInfoService.updateById(playballFieldInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "场地信息-通过id删除")
	@ApiOperation(value="场地信息-通过id删除", notes="场地信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		playballFieldInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "场地信息-批量删除")
	@ApiOperation(value="场地信息-批量删除", notes="场地信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.playballFieldInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "场地信息-通过id查询")
	@ApiOperation(value="场地信息-通过id查询", notes="场地信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PlayballFieldInfo playballFieldInfo = playballFieldInfoService.getById(id);
		return Result.ok(playballFieldInfo);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param playballFieldInfo
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, PlayballFieldInfo playballFieldInfo) {
      return super.exportXls(request, playballFieldInfo, PlayballFieldInfo.class, "场地信息");
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
      return super.importExcel(request, response, PlayballFieldInfo.class);
  }

}
