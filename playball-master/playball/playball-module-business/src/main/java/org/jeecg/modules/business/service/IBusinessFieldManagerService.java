package org.jeecg.modules.business.service;

import org.jeecg.modules.business.entity.BusinessFieldInfo;
import org.jeecg.modules.business.model.BusinessFieldInfoPage;
import org.jeecg.modules.business.model.BusinessFieldOrderPage;

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
	
	/**通过商户id，获取属于该商户的所有订单*/
	public IPage<BusinessFieldOrderPage> getFieldOrderByBusinessId(IPage page, String businessId);
}

