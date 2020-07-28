package org.jeecg.modules.appapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules. appapi.entity.Game;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 比赛
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface GameMapper extends BaseMapper<Game> {
    List<Game> queryByFieldId(Integer id);

    List<Game> queryByBid(String id);
}
