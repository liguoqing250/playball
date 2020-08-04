package org.jeecg.modules.appapi.service;

import io.swagger.models.auth.In;
import org.jeecg.modules. appapi.entity.GameSchedule;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.appapi.entity.vo.GameScheduleVo;

import java.util.List;

/**
 * @Description: 比赛
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface IGameScheduleService extends IService<GameSchedule> {

    List<GameSchedule> queryByGameId(String id);

    List<GameScheduleVo> queryTeamGameScore(Integer team_id);

    GameScheduleVo queryTeamGameInfoById(Integer id);
}
