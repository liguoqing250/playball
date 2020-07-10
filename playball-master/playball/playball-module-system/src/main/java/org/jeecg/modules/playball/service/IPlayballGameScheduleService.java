package org.jeecg.modules.playball.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import org.jeecg.modules.playball.entity.PlayballGames;
import org.jeecg.modules.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.vo.PlayballScheduleResultVo;
import org.jeecg.modules.playball.vo.PlayballScheduleInfoPage;
import org.jeecg.modules.playball.entity.PlayballGameSchedule;

import java.util.List;

/**
 * <p>
 * 
 * <p>
 * 
 * @Author:Steve
 * @Since：   2019-01-22
 */
public interface IPlayballGameScheduleService extends IService<PlayballGameSchedule>{
	
	public IPage<PlayballScheduleInfoPage> getAllSchedule(IPage page);
	public void addMatchInfo(PlayballGames gamesInfo, List<List<PlayballTeam>> matchList);
	
	public List<PlayballTeam> getEnrollTeamByGamesId(Integer gamesId);
	public List<PlayballTeam> getNextTeamList(Integer gamesId, Integer stage);
	
	public List<List<PlayballScheduleResultVo>> getScheduleListByStage(Integer gamesId, Integer stage);
	 
}
