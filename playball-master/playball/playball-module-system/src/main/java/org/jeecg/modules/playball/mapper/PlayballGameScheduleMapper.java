package org.jeecg.modules.playball.mapper;

import java.util.List;


import org.jeecg.modules.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.vo.PlayballScheduleInfoPage;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.playball.entity.PlayballGameSchedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface PlayballGameScheduleMapper extends BaseMapper<PlayballGameSchedule>{
	
	public List<PlayballTeam> getEnrollTeamByGamesId(@Param("gamesId")Integer gamesId);
	
	public List<PlayballGameSchedule> getScheduleListByStage(@Param("gamesId")Integer gamesId, @Param("stage")Integer stage);
	
	public PlayballTeam getTeamById(@Param("id")Integer id);
	
	public List<PlayballScheduleInfoPage> getAllSchedule(IPage page);
	public Integer getAllScheduleTotal();
}
