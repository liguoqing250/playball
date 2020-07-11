package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules.appapi.entity.AppointmentGames;
import org.jeecg.modules.appapi.entity.JoinAppointmentGamesPlayer;
import org.jeecg.modules.appapi.mapper.AppointmentGamesMapper;
import org.jeecg.modules.appapi.service.IAppointmentGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 约球
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Service
public class AppointmentGamesServiceImpl extends ServiceImpl<AppointmentGamesMapper, AppointmentGames> implements IAppointmentGamesService {

    @Autowired
    AppointmentGamesMapper appointmentGamesMapper;

    @Override
    public List<JoinAppointmentGamesPlayer> queryJoinAppointmentGamesPlayers(String id) {
        List<JoinAppointmentGamesPlayer> list=new ArrayList<>();
        AppointmentGames appointmentGames= appointmentGamesMapper.selectById(id);
        if(appointmentGames.getTpId()!=null){
            //团队
            list=appointmentGamesMapper.queryJoinPlayersByTeam(Integer.parseInt(id));
        }else{
            //个人
            list=appointmentGamesMapper.queryJoinPlayersByUsers(Integer.parseInt(id));
        }
        return list;
    }
}
