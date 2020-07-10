package org.jeecg.modules.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.business.entity.BusinessInfo;
import org.jeecg.modules.business.entity.BusinessSportsType;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 
 * @Author: 
 * @Date:  
 * @Version: V1.0
 */
public interface BusinessCommonMapper extends BaseMapper<BusinessSportsType> {
	
	public List<BusinessSportsType> getSportsTypeList();
	
	public List<BusinessInfo> getBusinessInfo();
	
	public String getBusinessIdByUserId(@Param("userId")String userId);
}
