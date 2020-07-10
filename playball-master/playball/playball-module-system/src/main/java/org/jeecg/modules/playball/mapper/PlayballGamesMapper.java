package org.jeecg.modules.playball.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.playball.entity.PlayballGames;
import org.jeecg.modules.playball.vo.PlayballGamesPage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface PlayballGamesMapper extends BaseMapper<PlayballGames>{
	
	public List<PlayballGamesPage> getAllGames(IPage page);
	public Integer getAllGamesTotal();
	public PlayballGamesPage getGamesInfoById(@Param("id")Integer id);
}
