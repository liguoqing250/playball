package org.jeecg.modules.business.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.jeecg.modules.business.entity.BusinessInfo;
import org.jeecg.modules.business.entity.BusinessSportsType;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 
 * @Author:
 * @Date:
 * @Version: V1.0
 */
public interface IBusinessCommonService extends IService<BusinessSportsType> {
	
	public List<BusinessSportsType> getSportsTypeList();
	
	public List<BusinessInfo> getBusinessInfo();
	
	public String getBusinessIdByUserId(String userId);
	
}
