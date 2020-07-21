package org.jeecg.modules.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.playball.entity.PlayballFieldInfo;
import org.jeecg.common.playball.vo.PlayballFieldInfoModel;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 场地信息
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
public interface PlayballFieldInfoMapper extends BaseMapper<PlayballFieldInfo> {
	
	public List<PlayballFieldInfoModel> getFieldInfoListByUserId(IPage page, @Param("fieldInfo")PlayballFieldInfoModel fieldInfo, @Param("userId")String userId);
	public Integer getFieldInfoListByUserIdTotal(@Param("fieldInfo")PlayballFieldInfoModel fieldInfo, @Param("userId")String userId);
	
	public List<PlayballFieldInfoModel> getFieldInfoBySportsId(@Param("sportsId")Integer sportsId);
	
	public String getBusinessIdByUserId(@Param("userId")String userId);
}
