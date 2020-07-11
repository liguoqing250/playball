package org.jeecg.modules.business.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import org.jeecg.modules.business.entity.BusinessFieldInfo;
import org.jeecg.modules.business.mapper.BusinessFieldManagerMapper;


import org.jeecg.modules.business.service.IBusinessFieldManagerService;
import org.jeecg.modules.business.model.BusinessFieldInfoPage;
import org.jeecg.modules.business.model.BusinessFieldOrderPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 
 * @Author: 
 * @Date:  
 * @Version: V1.0
 */
@Service
@Slf4j
public class BusinessFieldManagerServiceImpl extends ServiceImpl<BusinessFieldManagerMapper, BusinessFieldInfo> implements IBusinessFieldManagerService {
	
	@Autowired
	private BusinessFieldManagerMapper fieldrMapper;
	
	@Override
	public IPage<BusinessFieldInfoPage> getAllFieldInfoByUserId(IPage page, String userId) {

		List<BusinessFieldInfoPage> list = baseMapper.getAllFieldInfoByUserId(page, userId);
		Integer total = baseMapper.getAllFieldInfoTotalByUserId(userId);
	       
		IPage<BusinessFieldInfoPage> result = new Page<>(page.getCurrent(), page.getSize(), total);
		result.setRecords(list);

		return result;
	}
	
	@Override
	public IPage<BusinessFieldInfoPage> getAllFieldInfoBySportsId(IPage page, Integer sportsId) {
		
		List<BusinessFieldInfoPage> list = baseMapper.getAllFieldInfoBySportsId(page, sportsId);
		Integer total = baseMapper.getAllFieldInfoTotalBySportsId(sportsId);
	       
		IPage<BusinessFieldInfoPage> result = new Page<>(page.getCurrent(), page.getSize(), total);
		result.setRecords(list);

		return result;
	}
	
	@Override
	@Transactional
	public void addField(BusinessFieldInfo businessFieldInfo) {
		this.save(businessFieldInfo);
	}
	
	@Override
	@Transactional
	public Boolean updateField(BusinessFieldInfo businessFieldInfo) {
		if (businessFieldInfo != null) {
			businessFieldInfo.setUpdateTime(new Date());
			this.updateById(businessFieldInfo);
			return true;
		} else {
			return false;
		}

	}
	
	public IPage<BusinessFieldOrderPage> getFieldOrderByBusinessId(IPage page, String businessId){
		
		List<BusinessFieldOrderPage> list = fieldrMapper.getFieldOrderByBusinessId(page, businessId);
		Integer total = fieldrMapper.getFieldOrderByBusinessIdTotal(page, businessId);
		IPage<BusinessFieldOrderPage> result = new Page<>(page.getCurrent(), page.getSize(), total);
		
		result.setRecords(list);
		return result;
	}
}
