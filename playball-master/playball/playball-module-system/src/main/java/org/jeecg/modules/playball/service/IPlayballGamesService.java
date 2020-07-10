package org.jeecg.modules.playball.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import org.jeecg.modules.business.model.BusinessFieldInfoPage;
import org.jeecg.modules.playball.entity.PlayballGames;
import org.jeecg.modules.playball.vo.PlayballGamesPage;

import java.util.List;

/**
 * <p>
 * 
 * <p>
 * 
 * @Author:Steve
 * @Since：   2019-01-22
 */
public interface IPlayballGamesService extends IService<PlayballGames>{
	
	public IPage<PlayballGamesPage> getAllGames(IPage page);
	
	public void saveGames(PlayballGames playballGames);
	
	public void updateGames(PlayballGames playballGames);
	
	public PlayballGamesPage getGamesInfoById(Integer id);
}
