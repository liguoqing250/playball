package org.jeecg.modules.playball.service.impl;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballPlayer;
import org.jeecg.common.playball.vo.PlayballPlayerModel;
import org.jeecg.modules.playball.mapper.PlayballPlayerMapper;
import org.jeecg.modules.playball.service.IPlayballPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 队员管理
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Service
public class PlayballPlayerServiceImpl extends ServiceImpl<PlayballPlayerMapper, PlayballPlayer> implements IPlayballPlayerService {
	@Autowired
	private PlayballPlayerMapper playMapper;
	
	public IPage<PlayballPlayerModel> queryList(IPage page, PlayballPlayerModel playerModel){
		List<PlayballPlayerModel> list = playMapper.getPlayerList(page, playerModel);
		Integer total = playMapper.getPlayerListTotal(playerModel);
		
		IPage<PlayballPlayerModel> result = new Page<>(page.getCurrent(),page.getSize(),total);
		result.setRecords(list);
		
		return result;
	}
	
	public List<PlayballPlayerModel> getGamePlayersList(String teamId, String gameId, String scheduleId) {
		return playMapper.getGamePlayersList(Integer.valueOf(teamId),Integer.valueOf(gameId),Integer.valueOf(scheduleId));
	}
}
