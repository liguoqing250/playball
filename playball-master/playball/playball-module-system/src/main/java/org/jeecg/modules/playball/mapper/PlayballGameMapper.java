package org.jeecg.modules.playball.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.playball.entity.PlayballGame;
import org.jeecg.modules.playball.vo.PlayballGamesPage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description: 赛事信息
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
public interface PlayballGameMapper extends BaseMapper<PlayballGame> {
	
	public List<PlayballGamesPage> getGameList(IPage page, @Param("game")PlayballGame game);
	public Integer getAllGameListTotal(@Param("game")PlayballGame game);
	
	public PlayballGamesPage getGameInfoById(@Param("id") Integer id);
	
	public void updateStageById(@Param("stage")Integer stage, @Param("gameId")Integer gameId);
}
