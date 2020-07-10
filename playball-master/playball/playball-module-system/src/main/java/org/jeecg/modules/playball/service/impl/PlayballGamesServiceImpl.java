package org.jeecg.modules.playball.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jeecg.modules.playball.entity.PlayballGames;
import org.jeecg.modules.playball.service.IPlayballGamesService;
import org.jeecg.modules.playball.vo.PlayballGamesPage;
import org.jeecg.modules.playball.mapper.PlayballGamesMapper;
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
public class PlayballGamesServiceImpl extends ServiceImpl<PlayballGamesMapper, PlayballGames> implements IPlayballGamesService {
	
	@Autowired
	private PlayballGamesMapper playballGamesMapper;
	
	@Override
	public IPage<PlayballGamesPage> getAllGames(IPage page){
		List<PlayballGamesPage> list = baseMapper.getAllGames(page);
		
		Integer total = baseMapper.getAllGamesTotal();
	       
		IPage<PlayballGamesPage> result = new Page<>(page.getCurrent(), page.getSize(), total);
		result.setRecords(list);

		return result;
	}
	
	@Override
	public void saveGames(PlayballGames playballGames) {
		this.save(playballGames);
	}
	
	@Override
	public void updateGames(PlayballGames playballGames) {
		log.info("PlayballGamesServiceImpl="+playballGames);
		this.updateById(playballGames);
	}
	
	public PlayballGamesPage getGamesInfoById(Integer id) {
		return baseMapper.getGamesInfoById(id);
	}
}
