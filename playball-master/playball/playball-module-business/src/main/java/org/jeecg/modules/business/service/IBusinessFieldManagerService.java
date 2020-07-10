package org.jeecg.modules.business.service;

import org.jeecg.modules.business.entity.BusinessFieldInfo;
import org.jeecg.modules.business.model.BusinessFieldInfoPage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 
 * @Author:
 * @Date:
 * @Version: V1.0
 */
public interface IBusinessFieldManagerService extends IService<BusinessFieldInfo> {
	
	public IPage<BusinessFieldInfoPage> getAllFieldInfoByUserId(IPage page, String userId);
	
	public IPage<BusinessFieldInfoPage> getAllFieldInfoBySportsId(IPage page, Integer sportsId);
	
	public void addField(BusinessFieldInfo businessFieldInfo);

	public Boolean updateField(BusinessFieldInfo businessFieldInfo);
}

