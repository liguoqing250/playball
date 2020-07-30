package org.jeecg.modules.appapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules. appapi.entity.GameSchedule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.appapi.entity.vo.GameScheduleVo;

/**
 * @Description: 比赛
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface GameScheduleMapper extends BaseMapper<GameSchedule> {
    List<GameScheduleVo> queryTeamGameScore(Integer team_id);
}
