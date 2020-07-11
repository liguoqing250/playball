package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules. appapi.entity.GameSchedule;
import org.jeecg.modules.appapi.mapper.AppTeamMapper;
import org.jeecg.modules. appapi.mapper.GameScheduleMapper;
import org.jeecg.modules. appapi.service.IGameScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 比赛
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Service
public class GameScheduleServiceImpl extends ServiceImpl<GameScheduleMapper, GameSchedule> implements IGameScheduleService {

    @Autowired
    GameScheduleMapper gameScheduleMapper;
    @Autowired
    AppTeamMapper appTeamMapper;
    @Override
    public List<GameSchedule> queryByGameId(String id) {
        Map<String,Object> map=new HashMap<>();
        map.put("games_id",id);
        List<GameSchedule> list=gameScheduleMapper.selectByMap(map);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTeam1(appTeamMapper.selectById(list.get(i).getTeamId()));
            if(list.get(i).getOpponentId()!=null){
                list.get(i).setTeam2(appTeamMapper.selectById(list.get(i).getOpponentId()));
            }

        }
        return list;
    }
}
