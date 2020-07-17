package org.jeecg.modules.playball.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.playball.entity.PlayballEnroll;
import org.jeecg.modules.playball.entity.PlayballTeam;
import org.jeecg.modules.playball.vo.PlayballEnrollPage;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description: 报名球队管理
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
public interface PlayballEnrollMapper extends BaseMapper<PlayballEnroll> {
	
	public List<PlayballEnrollPage>  getEnrollList(IPage page, @Param("enrollPage")PlayballEnrollPage enrollPage);
	public Integer getEnrollListTotal(@Param("enrollPage")PlayballEnrollPage enrollPage);
	
	public List<PlayballTeam> getEnrollTeamByGamesId(@Param("gamesId")Integer gamesId);
	
}
