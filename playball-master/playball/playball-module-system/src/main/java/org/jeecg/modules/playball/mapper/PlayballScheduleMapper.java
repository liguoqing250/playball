package org.jeecg.modules.playball.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.playball.entity.PlayballSchedule;
import org.jeecg.modules.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.vo.PlayballScheduleInfoPage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 赛程表
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
public interface PlayballScheduleMapper extends BaseMapper<PlayballSchedule> {
	
	public List<PlayballScheduleInfoPage> getSchedulePageList(IPage page, @Param("scheduleInfo")PlayballScheduleInfoPage scheduleInfo);
	
	public Integer getSchedulePageListTotle(@Param("scheduleInfo")PlayballScheduleInfoPage scheduleInfo);
	
	public List<PlayballSchedule> getScheduleListByStage(@Param("gamesId")Integer gamesId, @Param("stage")Integer stage);
	
	public List<PlayballSchedule> getScheduleListByGameId(@Param("gameId")Integer gameId);
	
	public List<PlayballSchedule> getAllScheduleListByGameId(@Param("gameId")Integer gameId);
	
	public List<PlayballScheduleInfoPage> getMacthListByGameId(@Param("gameId")Integer gameId);
	
}
