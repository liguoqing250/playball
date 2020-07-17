package org.jeecg.modules.playball.service.impl;

import java.util.List;
import java.util.Map;

import org.jeecg.modules.playball.entity.PlayballGame;
import org.jeecg.modules.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.mapper.PlayballGameMapper;
import org.jeecg.modules.playball.service.IPlayballGameService;
import org.jeecg.modules.playball.vo.PlayballGamesPage;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 赛事信息
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Service
@Slf4j
public class PlayballGameServiceImpl extends ServiceImpl<PlayballGameMapper, PlayballGame> implements IPlayballGameService {
	
	@Autowired
	private PlayballGameMapper gameMapper;
	
	@Override
	public IPage<PlayballGamesPage> queryGameList(IPage page, PlayballGame playballGame){
		
		List<PlayballGamesPage> list = gameMapper.getGameList(page,playballGame);
		Integer total = gameMapper.getAllGameListTotal(playballGame);
	       
		IPage<PlayballGamesPage> result = new Page<>(page.getCurrent(), page.getSize(), total);
		result.setRecords(list);

		return result;
	}
	
	@Override
	public PlayballGamesPage getGameInfoById(Integer id) {
		return gameMapper.getGameInfoById(id);
	}
}
