package org.jeecg.modules.playball.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.playball.entity.PlayballSportsData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 运动数据类型
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface PlayballSportsDataMapper extends BaseMapper<PlayballSportsData> {
	
	public List<PlayballSportsData> getListBySportsId(@Param("sportsId")Integer sportsId);
}
