package org.jeecg.modules.playball.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.playball.entity.PlayballScheduleUserData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 球员数据统计
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface PlayballScheduleUserDataMapper extends BaseMapper<PlayballScheduleUserData> {
	
	public List<PlayballScheduleUserData> getListBySportsId(@Param("sportsId")Integer sportsId);
	
}
