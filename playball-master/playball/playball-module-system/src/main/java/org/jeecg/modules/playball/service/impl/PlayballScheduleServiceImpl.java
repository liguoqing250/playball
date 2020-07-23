package org.jeecg.modules.playball.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.playball.entity.PlayballSchedule;
import org.jeecg.common.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.mapper.PlayballScheduleMapper;
import org.jeecg.modules.playball.service.IPlayballScheduleService;
import org.jeecg.modules.playball.service.IPlayballTeamService;
import org.jeecg.modules.playball.vo.PlayballScheduleInfoPage;
import org.jeecg.modules.playball.vo.PlayballScheduleResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
