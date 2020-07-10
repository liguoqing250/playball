package org.jeecg.modules.business.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.jeecg.common.api.vo.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.jeecg.modules.business.entity.BusinessInfo;
import org.jeecg.modules.business.entity.BusinessSportsType;
import org.jeecg.modules.business.service.IBusinessCommonService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 
 * @Author: 
 * @Date:
 * @Version:
 */
@RestController
@RequestMapping("/bm/common")
@Slf4j

public class BusinessCommonController {
	@Autowired
    private IBusinessCommonService businessCommonService;
    

    /**
     * 分页列表查询
     *
     * @param 
     * @param 
     * @param 
     * @param 
     * @return
     */
    @GetMapping(value = "/sportslist")
    public Result<?> querySportsList(BusinessSportsType businessSportsType, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
       
    	List<BusinessSportsType> list = businessCommonService.getSportsTypeList();
        return Result.ok(list);
    }
    
    @GetMapping(value = "/businesslist")
    public Result<?> queryBusinessList(BusinessInfo businessInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
       
    	List<BusinessInfo> list = businessCommonService.getBusinessInfo();
        return Result.ok(list);
    }
    
}
