package org.jeecg.modules.system.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.PmsUtil;
import org.jeecg.common.util.oConvertUtils;

import org.jeecg.modules.system.entity.SysBusinessInfo;
import org.jeecg.modules.system.service.ISysBusinessInfoService;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 
 * </p>
 *
 * @Author
 * @since 
 */
@RestController
@RequestMapping("/sys/businessinfo")
@Slf4j
public class SysBusinessInfoController {
	@Autowired
	private ISysBusinessInfoService sysBusinessInfoService;

	/**
	  * 分页列表查询
	 * @param role
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Result<IPage<SysBusinessInfo>> queryPageList(SysBusinessInfo role,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<SysBusinessInfo>> result = new Result<IPage<SysBusinessInfo>>();
		QueryWrapper<SysBusinessInfo> queryWrapper = QueryGenerator.initQueryWrapper(role, req.getParameterMap());
		Page<SysBusinessInfo> page = new Page<SysBusinessInfo>(pageNo, pageSize);
		IPage<SysBusinessInfo> pageList = sysBusinessInfoService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param role
	 * @return
	 */
	/*@RequestMapping(value = "/add", method = RequestMethod.POST)
	//@RequiresRoles({"admin"})
	public Result<SysBusinessInfo> add(@RequestBody SysBusinessInfo role) {
		Result<SysBusinessInfo> result = new Result<SysBusinessInfo>();
		try {
			role.setCreateTime(new Date());
			sysBusinessInfoService.save(role);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.error500("操作失败");
		}
		return result;*/
	
	/**
	  *  编辑
	 * @param role
	 * @return
	 */
	//@RequiresRoles({"admin"})
	/*@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Result<SysBusinessInfo> edit(@RequestBody SysBusinessInfo role) {
		Result<SysBusinessInfo> result = new Result<SysBusinessInfo>();
		SysBusinessInfo sysrole = sysBusinessInfoService.getById(role.getId());
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			role.setUpdateTime(new Date());
			boolean ok = sysBusinessInfoService.updateById(role);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		return result;
	}*/
		
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	//@RequiresRoles({"admin"})
	/*@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		sysBusinessInfoService.deleteRole(id);
		return Result.ok("删除角色成功");
	}*/
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	//@RequiresRoles({"admin"})
	/*@RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
	public Result<SysBusinessInfo> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<SysBusinessInfo> result = new Result<SysBusinessInfo>();
		if(oConvertUtils.isEmpty(ids)) {
			result.error500("未选中角色！");
		}else {
			sysBusinessInfoService.deleteBatchRole(ids.split(","));
			result.success("删除角色成功!");
		}
		return result;
	}*/
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	/*@RequestMapping(value = "/queryById", method = RequestMethod.GET)
	public Result<SysBusinessInfo> queryById(@RequestParam(name="id",required=true) String id) {
		Result<SysBusinessInfo> result = new Result<SysBusinessInfo>();
		SysBusinessInfo sysrole = sysBusinessInfoService.getById(id);
		if(sysrole==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysrole);
			result.setSuccess(true);
		}
		return result;
	}
	*/

	
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
	public Result<List<SysBusinessInfo>> queryall() {
		Result<List<SysBusinessInfo>> result = new Result<>();
		List<SysBusinessInfo> list = sysBusinessInfoService.list();
		log.info("sdfffffffffffff");
		if(list==null||list.size()<=0) {
			result.error500("未找到角色信息");
		}else {
			for(int i=0;i<list.size();i++) {

				JSONObject json = (JSONObject) JSONObject.toJSON(list.get(i));
				String jsonString = JSONObject.toJSONString(json);
				log.info(jsonString);

			}
			result.setResult(list);
			result.setSuccess(true);
		}
		return result;
	}
	
	
}
