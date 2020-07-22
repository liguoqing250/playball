package org.jeecg.modules.playball.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.playball.entity.PlayballSportsPosition;
import org.jeecg.common.playball.vo.PlayballSportsPositionModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 担任位置
 * @Author: jeecg-boot
 * @Date:   2020-07-22
 * @Version: V1.0
 */
public interface PlayballSportsPositionMapper extends BaseMapper<PlayballSportsPosition> {
	public List<PlayballSportsPositionModel> getPositionList(IPage page,@Param("positionModel")PlayballSportsPositionModel positionModel);
	public Integer getPositionListTotal(@Param("positionModel")PlayballSportsPositionModel positionModel);
	
	List<PlayballSportsPositionModel> getPositionListBySportsId(@Param("sportsId")Integer sportsId);
}
