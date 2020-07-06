package org.jeecg.modules.appapi.service.impl;


import org.jeecg.modules.appapi.entity.AppTeamPlayers;
import org.jeecg.modules.appapi.mapper.AppTeamPlayersMapper;
import org.jeecg.modules.appapi.service.AppTeamPlayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
}
