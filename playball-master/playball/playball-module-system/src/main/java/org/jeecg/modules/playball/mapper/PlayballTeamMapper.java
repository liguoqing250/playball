package org.jeecg.modules.playball.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.playball.entity.PlayballTeam;
import org.jeecg.common.playball.vo.PlayballTeamModel;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description: 球队查看
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
public interface PlayballTeamMapper extends BaseMapper<PlayballTeam> {
	public List<PlayballTeamModel> getTeamList(IPage page, @Param("team")PlayballTeamModel team);
	public Integer getTeamListTotal(@Param("team")PlayballTeamModel team);
}
