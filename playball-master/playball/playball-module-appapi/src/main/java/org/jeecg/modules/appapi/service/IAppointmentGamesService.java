package org.jeecg.modules.appapi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.appapi.entity.AppointmentGames;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.appapi.entity.JoinAppointmentGamesPlayer;
import org.jeecg.modules.appapi.entity.bo.AppointmentGamesBo;
import org.jeecg.modules.appapi.entity.vo.AppointmentGamesVo;

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
