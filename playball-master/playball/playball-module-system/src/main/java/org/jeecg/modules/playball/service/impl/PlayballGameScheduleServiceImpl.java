package org.jeecg.modules.playball.service.impl;


import java.util.List;
import java.util.ArrayList;

import org.jeecg.modules.playball.entity.PlayballGames;
import org.jeecg.modules.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.vo.PlayballScheduleResultVo;
import org.jeecg.modules.playball.vo.PlayballScheduleInfoPage;
import org.jeecg.modules.playball.entity.PlayballGameSchedule;
import org.jeecg.modules.playball.service.IPlayballGamesService;
import org.jeecg.modules.playball.service.IPlayballGameScheduleService;
import org.jeecg.modules.playball.mapper.PlayballGameScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 
 * </p>
 *
 * @Author 
 * @since 
 */
@Service
@Slf4j
public class PlayballGameScheduleServiceImpl extends ServiceImpl<PlayballGameScheduleMapper, PlayballGameSchedule> implements IPlayballGameScheduleService {
	
	@Autowired
	private IPlayballGamesService playballGamesService;
	
	@Override
	public IPage<PlayballScheduleInfoPage> getAllSchedule(IPage page){
		
		List<PlayballScheduleInfoPage> list = baseMapper.getAllSchedule(page);
		log.info("PlayballScheduleInfoPage"+list);
		Integer total = baseMapper.getAllScheduleTotal();
	       
		IPage<PlayballScheduleInfoPage> result = new Page<>(page.getCurrent(), page.getSize(), total);
		result.setRecords(list);

		return result;
	}
	
	@Override
	public void addMatchInfo(PlayballGames gamesInfo, List<List<PlayballTeam>> matchList) {

		Integer stage = gamesInfo.getStage()+1;
		gamesInfo.setStage(stage);
		log.info("addMatchInfo====gamesInfo"+gamesInfo);
		for(int i=0; i<matchList.size(); i++) {
			//获取要比赛的两只球队
			PlayballGameSchedule gameSchedule = new PlayballGameSchedule();
			if(matchList.get(i).size() == 2) {
				gameSchedule.setGamesId(gamesInfo.getId());
				gameSchedule.setTeamId(matchList.get(i).get(0).getTeamId());
				gameSchedule.setOpponentId(matchList.get(i).get(1).getTeamId());
				gameSchedule.setStage(stage);
				this.save(gameSchedule);
			}else {
				//轮空球队
				gameSchedule.setGamesId(gamesInfo.getId());
				gameSchedule.setTeamId(matchList.get(i).get(0).getTeamId());
				gameSchedule.setGameStatus(1);//表示已赛
				gameSchedule.setGameResult(1);//表示赢球
				gameSchedule.setStage(stage);
				this.save(gameSchedule);
			}
		}
		playballGamesService.updateGames(gamesInfo);
	}
	
	@Override
	public List<PlayballTeam> getEnrollTeamByGamesId(Integer gamesId){
		log.info("gamesId="+gamesId);
		return baseMapper.getEnrollTeamByGamesId(gamesId);
	}
	
	@Override
	public List<PlayballTeam> getNextTeamList(Integer gamesId, Integer stage){
		List<PlayballTeam> teamList = new ArrayList<PlayballTeam>();
		
		List<PlayballGameSchedule> scheduelList = baseMapper.getScheduleListByStage(gamesId, stage);
		for(int j=0; j<scheduelList.size(); j++) {
			PlayballTeam teamInfo = new PlayballTeam();
			if(scheduelList.get(j).getGameResult() == 1) {
				//胜利获取teamid
				teamInfo = baseMapper.getTeamById(scheduelList.get(j).getTeamId());
			}else if(scheduelList.get(j).getGameResult()==3) {
				//失败获取opponentId
				teamInfo = baseMapper.getTeamById(scheduelList.get(j).getOpponentId());
			}else {
				//平局
			}
			teamList.add(teamInfo);
		}
		
		return teamList;
	}
	
	@Override
	public List<List<PlayballScheduleResultVo>> getScheduleListByStage(Integer gamesId, Integer stage){
		List<List<PlayballScheduleResultVo>> gameScheduleResultList = new ArrayList<List<PlayballScheduleResultVo>>();

		for(int i=1; i<=stage; i++) {
			List<PlayballGameSchedule> scheduelList = baseMapper.getScheduleListByStage(gamesId, i);
			List<PlayballScheduleResultVo> scheduleResultList = new ArrayList<PlayballScheduleResultVo>();
			
			for(int j=0; j<scheduelList.size(); j++) {
				PlayballTeam team = baseMapper.getTeamById(scheduelList.get(j).getTeamId());
				log.info("team="+team);
				PlayballTeam topponent = baseMapper.getTeamById(scheduelList.get(j).getOpponentId());
				log.info("topponent="+topponent);
				
				PlayballScheduleResultVo scheduleResultVo = new PlayballScheduleResultVo();
				scheduleResultVo.setGameSchedule(scheduelList.get(j));
				scheduleResultVo.setTeam(team);
				if(topponent != null) {
					scheduleResultVo.setTopponent(topponent);
				}
				scheduleResultList.add(scheduleResultVo);
			}
			if(scheduleResultList != null) {
				gameScheduleResultList.add(scheduleResultList);
			}
			log.info("gameScheduleResultList="+gameScheduleResultList);
		}
		
		return gameScheduleResultList;
	}
	
}
