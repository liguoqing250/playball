package org.jeecg.modules.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.business.entity.BusinessFieldInfo;
import org.jeecg.modules.business.model.BusinessFieldInfoPage;
import org.jeecg.modules.business.model.BusinessFieldOrderPage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description: 
 * @Author: 
 * @Date:  
 * @Version: V1.0
 */
public interface BusinessFieldManagerMapper extends BaseMapper<BusinessFieldInfo> {
	
	public List<BusinessFieldInfoPage> getAllFieldInfoByUserId(IPage page, @Param("userId") String userId);
	public Integer getAllFieldInfoTotalByUserId(@Param("userId")String userId);
	
	public List<BusinessFieldInfoPage> getAllFieldInfoBySportsId(IPage page, @Param("sportsId")Integer  sportsId);
	public Integer getAllFieldInfoTotalBySportsId(@Param("sportsId")Integer  sportsId);
	
	public List<BusinessFieldOrderPage> getFieldOrderByBusinessId(IPage page, @Param("businessId") String businessId);
	public Integer getFieldOrderByBusinessIdTotal(IPage page,  @Param("businessId") String businessId);

}
