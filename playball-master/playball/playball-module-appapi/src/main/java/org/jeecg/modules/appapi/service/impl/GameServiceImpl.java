package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules. appapi.entity.Game;
import org.jeecg.modules. appapi.mapper.GameMapper;
import org.jeecg.modules. appapi.service.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 比赛
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Service
public class GameServiceImpl extends ServiceImpl<GameMapper, Game> implements IGameService {

    @Autowired
    GameMapper gameMapper;
    @Override
    public List<Game> queryByFieldId(Integer id) {
        return gameMapper.queryByFieldId(id);
    }
}
