package org.jeecg.modules.appapi.service;

import org.jeecg.modules.appapi.entity.AppointmentGames;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.appapi.entity.JoinAppointmentGamesPlayer;

import java.util.List;

/**
 * @Description: 约球
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface IAppointmentGamesService extends IService<AppointmentGames> {

    List<JoinAppointmentGamesPlayer> queryJoinAppointmentGamesPlayers(String id);
}
