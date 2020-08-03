package org.jeecg.modules.playball.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.playball.entity.PlayballSchedule;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.entity.PlayballGame;
import org.jeecg.modules.playball.mapper.PlayballScheduleMapper;
import org.jeecg.modules.playball.service.IPlayballEnrollService;
import org.jeecg.modules.playball.service.IPlayballGameService;
import org.jeecg.modules.playball.service.IPlayballScheduleService;
import org.jeecg.modules.playball.service.IPlayballTeamService;
import org.jeecg.modules.playball.util.GameUtils;
import org.jeecg.modules.playball.vo.PlayballScheduleInfoPage;
import org.jeecg.modules.playball.vo.PlayballScheduleResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 赛程表
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
@Service
@Slf4j
public class PlayballScheduleServiceImpl extends ServiceImpl<PlayballScheduleMapper, PlayballSchedule> implements IPlayballScheduleService {
	
	@Autowired
	private PlayballScheduleMapper scheduleMapper;
	
	@Autowired
	private IPlayballTeamService playballTeamService;
	
	@Autowired
	private IPlayballEnrollService enrollService;
	
	@Autowired
	private IPlayballGameService gameService;
	
	@Override
	public IPage<PlayballScheduleInfoPage>  querySchedulePageList(IPage page, PlayballScheduleInfoPage scheduleInfo){
		
		List<PlayballScheduleInfoPage>  list = scheduleMapper.getSchedulePageList(page, scheduleInfo);
		Integer total = scheduleMapper.getSchedulePageListTotle(scheduleInfo);
		IPage<PlayballScheduleInfoPage> result = new Page<>(page.getCurrent(), page.getSize(), total);
		
		result.setRecords(list);
		return result;
	}
	
	@Override
	public List<List<PlayballScheduleResultVo>> getScheduleListByStage(Integer gamesId, Integer stage){
		List<List<PlayballScheduleResultVo>> gameScheduleResultList = new ArrayList<List<PlayballScheduleResultVo>>();

		for(int i=1; i<=stage; i++) {
			List<PlayballSchedule> scheduelList = scheduleMapper.getScheduleListByStage(gamesId, i);
			List<PlayballScheduleResultVo> scheduleResultList = new ArrayList<PlayballScheduleResultVo>();
			
			for(int j=0; j<scheduelList.size(); j++) {
				PlayballTeam team = playballTeamService.getById(scheduelList.get(j).getTeamId());
				log.info("team="+team);
				PlayballTeam topponent = playballTeamService.getById(scheduelList.get(j).getOpponentId());
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
	
	
	public List<PlayballTeam> getWinTeamByGameId(Integer gameId){
		List<PlayballTeam> winTeamList = new ArrayList<PlayballTeam>();
		List<PlayballSchedule> scheduleList = scheduleMapper.getScheduleListByGameId(gameId);
		
		for(int i=0; i < scheduleList.size();i++) {
			PlayballTeam team = new PlayballTeam();
			if(scheduleList.get(i).getGameResult() == 1) {
				team = playballTeamService.getById(scheduleList.get(i).getTeamId());
			}else {
				team = playballTeamService.getById(scheduleList.get(i).getOpponentId());
			}
			winTeamList.add(team);
		}
		
		return winTeamList;
	}
	
	public List<PlayballSchedule> getScheduleListByGameId(Integer gameId){
		return scheduleMapper.getScheduleListByGameId(gameId);
	}
	
	public List<PlayballSchedule> getAllScheduleListByGameId(Integer gameId){
		return scheduleMapper.getAllScheduleListByGameId(gameId);
	}
	
	public List<PlayballScheduleInfoPage> getMacthListByGameId(Integer gameId){
		return scheduleMapper.getMacthListByGameId(gameId);
	}
	
	public List<List<PlayballScheduleInfoPage>> getMacthByGameId(Integer gameId){
		List<PlayballScheduleInfoPage> list = scheduleMapper.getMacthListByGameId(gameId);
		
		List<List<PlayballScheduleInfoPage>> matchList = GameUtils.groupDataByCondition(list, new Comparator<PlayballScheduleInfoPage>() {
		     @Override
		     public int compare(PlayballScheduleInfoPage o1, PlayballScheduleInfoPage o2) {
		         return o1.getStage().equals(o2.getStage()) ? 0 : -1;
		     }
		 });
		
		return matchList;
	}
	
	public JSONObject createMacthList(Integer gameId, String nextStage){	
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("finished", 0);
		  
		List<PlayballTeam> teamList = enrollService.getEnrollTeamByGamesId(gameId);
		if(teamList.size() <= 2) {
			jsonObj.put("finished", 1);
		}
		List<PlayballScheduleInfoPage> winMatchList = new ArrayList<PlayballScheduleInfoPage>();
		List<PlayballTeam> lostTeamList = new ArrayList<PlayballTeam>(); 
		boolean bRemove = true;
		if(nextStage.equals("true")) {
			//获取赢比赛的队伍，下一轮比赛队伍
			List<List<PlayballScheduleInfoPage>> matchList = this.getMacthByGameId(gameId);
			
			for(int j=0; j < matchList.size(); j++) {
				List<PlayballScheduleInfoPage> nextlist = matchList.get(j);
				if(nextlist.size()==2) {
					//剩两场比赛，说明只剩4只队伍，无需删除，胜者和胜者打，负方和负方打
					bRemove = false;
				}
				for(int i=0; i < nextlist.size(); i++) {
					Integer teamId = 0;
					if(nextlist.get(i).getGameResult() == 3) {
						teamId = nextlist.get(i).getTeamId();
					}else if(nextlist.get(i).getGameResult() == 1) {
						teamId = nextlist.get(i).getOpponentId();
					}
					//把输掉比赛的队伍从报名列表中移除
					for(int c=0; c<teamList.size();c++) {
						if(teamList.get(c).getTeamId() == teamId) {
							if(!bRemove) {
								lostTeamList.add(teamList.get(c));
							}
							teamList.remove(c);
							break;
						}
					}
				}
			}
		}
		if (teamList != null && teamList.size() > 0) {
      	  	//创建一个新集合
      	  	List<PlayballTeam> newList = new ArrayList<PlayballTeam>();
      	  	newList = GameUtils.disorganizeList(teamList);
      	  	//两两分组
      	  	if(!bRemove) {
      	  		for(int i=0; i<lostTeamList.size(); i++) {
      	  			newList.add(lostTeamList.get(i));
      	  		}
      	  		jsonObj.put("finished", 1);
      	  	}
      	  	List<List<PlayballTeam>> matchTempList = GameUtils.fixedGrouping(newList, 2);
      	  
			for(int i=0; i<matchTempList.size(); i++) {
				PlayballScheduleInfoPage resultModel = new PlayballScheduleInfoPage();
				
				resultModel.setGamesId(gameId);
				resultModel.setTeamId(matchTempList.get(i).get(0).getTeamId());
				resultModel.setTeamName(matchTempList.get(i).get(0).getTName());
				resultModel.setTeamImage(matchTempList.get(i).get(0).getTImage());
				if(matchTempList.get(i).size()==2) {
					resultModel.setOpponentId(matchTempList.get(i).get(1).getTeamId());
					resultModel.setOpponentName(matchTempList.get(i).get(1).getTName());
					resultModel.setTeamImage(matchTempList.get(i).get(1).getTImage());
				}
				
				winMatchList.add(resultModel);
			}
		}
		jsonObj.put("matchList", winMatchList);
		
		return jsonObj;
	}
	
	@Override
	public void addMatch(List<PlayballScheduleInfoPage> matchList, Integer gameId, Integer finished) {
		PlayballGame gameInfo =gameService.getById(gameId);
		Integer stage = gameInfo.getStage()+1;
		gameInfo.setStage(stage);
		
		if(finished == 1) {
		   gameInfo.setFinished(1);//更新赛事状态，表示赛事所有比赛结束啦
		}
		for(int i=0; i<matchList.size(); i++) {
			//获取要比赛的两只球队
			PlayballSchedule schedule = new PlayballSchedule();
			BeanUtils.copyProperties(matchList.get(i), schedule);
			log.info("-----addMatch-----"+schedule);
			schedule.setStage(stage);
			if(matchList.get(i).getOpponentId() == null) {
				//如果为空，轮空队伍3:0获胜
				schedule.setEnterBall(3);
				schedule.setGameResult(1);
				schedule.setGameStatus(1);
			}
			
			this.save(schedule);
		}
		gameService.updateById(gameInfo);
	}
	
	public void addLoopMatch(List<List<PlayballScheduleInfoPage>> matchList, Integer gameId, Integer finished) {
		PlayballGame gameInfo =gameService.getById(gameId);
		Integer stage = matchList.size();
		gameInfo.setStage(stage);
		
		if(finished == 1) {
		   gameInfo.setFinished(1);//更新赛事状态，表示赛事所有比赛结束啦
		}
		for(int i=0; i<matchList.size(); i++) {
			for(int j=0; j<matchList.get(i).size();j++) {
				//获取要比赛的两只球队
				PlayballScheduleInfoPage scheduleInfo = JSON.parseObject(JSON.toJSONString(matchList.get(i).get(j)),PlayballScheduleInfoPage.class);
				PlayballSchedule schedule = new PlayballSchedule();
				BeanUtils.copyProperties(scheduleInfo, schedule);
				schedule.setStage(i+1);
				if(scheduleInfo.getOpponentId() == null) {
					//如果为空，轮空队伍3:0获胜
					schedule.setEnterBall(3);
					schedule.setGameResult(1);
					schedule.setGameStatus(1);
				}
				
				this.save(schedule);
			}
		}
		
		gameService.updateById(gameInfo);
	}

}
