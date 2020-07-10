package org.jeecg.modules.business.service.impl;


import java.util.List;

import org.jeecg.modules.business.entity.BusinessInfo;
import org.jeecg.modules.business.entity.BusinessSportsType;
import org.jeecg.modules.business.service.IBusinessCommonService;
import org.jeecg.modules.business.mapper.BusinessCommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


/**
 * @Description: 
 * @Author:
 * @Date:
 * @Version: V1.0
 */
@Service
public class BusinessCommonServiceImpl extends ServiceImpl<BusinessCommonMapper, BusinessSportsType> implements IBusinessCommonService {

	@Autowired
	private BusinessCommonMapper businessCommonMapper;
	
	public List<BusinessSportsType> getSportsTypeList(){
		return businessCommonMapper.getSportsTypeList();
	}
	
	public List<BusinessInfo> getBusinessInfo(){
		return businessCommonMapper.getBusinessInfo();
	}
	
	public String getBusinessIdByUserId(String userId) {
		return businessCommonMapper.getBusinessIdByUserId(userId);
	}
}
