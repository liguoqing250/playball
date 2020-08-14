package org.jeecg.modules.appapi.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.AppointmentGames;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.appapi.entity.JoinAppointmentGamesPlayer;
import org.jeecg.modules.appapi.entity.bo.AppointmentGamesBo;
import org.jeecg.modules.appapi.entity.vo.AppointmentGamesVo;

/**
 * @Description: 约球
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface AppointmentGamesMapper extends BaseMapper<AppointmentGames> {

    List<JoinAppointmentGamesPlayer> queryJoinPlayersByTeam(Integer id);

    List<JoinAppointmentGamesPlayer> queryJoinPlayersByUsers(Integer id);

    void deleteByTeamId(Integer team_id);

    IPage<AppointmentGamesBo> listByUid(Page<AppointmentGamesVo> page, @Param("uId")Integer uId);
}
