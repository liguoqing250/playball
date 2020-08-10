package org.jeecg.modules.appapi.service.impl;


import org.apache.commons.collections.map.HashedMap;
import org.jeecg.modules.appapi.entity.AppTeamPlayers;
import org.jeecg.modules.appapi.entity.vo.AppTeamPlayersVo;
import org.jeecg.modules.appapi.mapper.AppTeamPlayersMapper;
import org.jeecg.modules.appapi.service.AppTeamPlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class AppTeamPlayersServiceImpl implements AppTeamPlayersService {

    @Autowired
    AppTeamPlayersMapper appTeamPlayersMapper;

    @Override
    public void insert(AppTeamPlayers record) {
        appTeamPlayersMapper.insert(record);
    }

    @Override
    public void deleteById(int id) {
        appTeamPlayersMapper.deleteById(id);
    }

    @Override
    public void update(AppTeamPlayers record) {
        appTeamPlayersMapper.update(record);
    }

    @Override
    public AppTeamPlayers selectById(int id) {
        return appTeamPlayersMapper.selectById(id);
    }

    @Override
    public List<AppTeamPlayersVo> selectByTeamId(int id,String playerName) {
        Map<String,Object> map =new HashedMap();
        map.put("id",id);
        map.put("playerName",playerName);
        return appTeamPlayersMapper.selectByTeamId(map);
    }

    @Override
    public void kickPlayer(Integer uid,Integer teamId) {
        AppTeamPlayers appTeamPlayers=new AppTeamPlayers();
        appTeamPlayers.setTeam_id(teamId);
        appTeamPlayers.setU_id(uid);
        appTeamPlayersMapper.kickPlayer(appTeamPlayers);
    }

    @Override
    public AppTeamPlayers selectByuId(Integer uId) {
        return appTeamPlayersMapper.selectByUserId(uId);
    }

    @Override
    public void deleteByUId(Integer u_id) {
        appTeamPlayersMapper.deleteByUId(u_id);
    }
}
