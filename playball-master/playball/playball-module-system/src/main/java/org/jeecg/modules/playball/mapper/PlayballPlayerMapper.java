package org.jeecg.modules.playball.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.playball.entity.PlayballPlayer;
import org.jeecg.common.playball.vo.PlayballPlayerModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @Description: 队员管理
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface PlayballPlayerMapper extends BaseMapper<PlayballPlayer> {
	public List<PlayballPlayerModel> getPlayerList(IPage page, @Param("playerModel")PlayballPlayerModel playerModel);
	public Integer getPlayerListTotal(@Param("playerModel")PlayballPlayerModel playerModel);
}
