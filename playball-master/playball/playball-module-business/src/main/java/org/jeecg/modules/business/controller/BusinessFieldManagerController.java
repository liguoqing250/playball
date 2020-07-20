package org.jeecg.modules.business.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;


import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.vo.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.jeecg.modules.business.entity.BusinessFieldInfo;
import org.jeecg.modules.business.service.IBusinessFieldManagerService;
import org.jeecg.modules.business.service.IBusinessCommonService;
import org.jeecg.modules.business.model.BusinessFieldInfoPage;
import org.jeecg.modules.business.model.BusinessFieldOrderPage;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 
 * @Author: 
 * @Date:
 * @Version: V2.0
 */
@RestController
@RequestMapping("/businessinfo/field")
@Slf4j
public class BusinessFieldManagerController extends JeecgController<BusinessFieldInfo, IBusinessFieldManagerService> {

    @Autowired
    private IBusinessFieldManagerService businessFieldManagerService;
    
    @Autowired
    private IBusinessCommonService businessCommonService;
    /**
     * 分页列表查询
     *
     * @param 
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<?> queryPageList(BusinessFieldInfo businessFieldInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
    	//获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //获取用户id后获取用户属于的商家id
        String userId = sysUser.getId();
        log.info("----获取场馆信息----"+userId);
        
    	IPage<BusinessFieldInfoPage> pageList = businessFieldManagerService.getAllFieldInfoByUserId(new Page(pageNo, pageSize),userId);
        return Result.ok(pageList);

    }
    
    /**
     * 分页列表查询
     *
     * @param 获取场馆信息的分页列表通过运动类型
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/listbsi")
    public Result<?> queryPageListBySports(BusinessFieldInfo businessFieldInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
    	Integer sportsId = Integer.valueOf(req.getParameter("sportsId"));
    	log.info("获取sportsid="+req.getParameter("sportsId"));
    			
    	IPage<BusinessFieldInfoPage> pageList = businessFieldManagerService.getAllFieldInfoBySportsId(new Page(pageNo, pageSize),sportsId);
        return Result.ok(pageList);

    }
    
    /**
     * 分页列表查询
     *
     * @param 获取场馆订单信息的分页列表通过商户id
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/orderlist")
    public Result<?> getFieldOrderByBusinessId(BusinessFieldOrderPage businessFieldOrderPage, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
    	//获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //获取用户id后获取用户属于的商家id
        String businessId = sysUser.getDepartIds();
        log.info("商户id = "+ businessId);
    			
    	IPage<BusinessFieldOrderPage> pageList = businessFieldManagerService.getFieldOrderByBusinessId(new Page(pageNo, pageSize),businessId);
        return Result.ok(pageList);

    }
    
    /**
     * 添加
     *
     * @param 
     * @return
     */
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody BusinessFieldInfo businessFieldInfo) {
    	//获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //获取用户id后获取用户属于的商家id
        String userId = sysUser.getId();
        String businessId = businessCommonService.getBusinessIdByUserId(userId);
        log.info("商家的id="+businessId);
        businessFieldInfo.setBusinessId(businessId);
    	businessFieldInfo.setCreateTime(new Date());
    	businessFieldInfo.setDelFlag("0");
    	businessFieldManagerService.addField(businessFieldInfo);
        return Result.ok("添加成功！");
    	
    }
   

    /**
     * 编辑
     *
     * @param 
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<?> eidt(@RequestBody BusinessFieldInfo businessFieldInfo) {
    	
    	businessFieldManagerService.updateField(businessFieldInfo);
        return Result.ok("编辑成功！");
    }


}
