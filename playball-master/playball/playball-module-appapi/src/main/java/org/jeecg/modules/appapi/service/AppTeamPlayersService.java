package org.jeecg.modules.appapi.service;

import org.jeecg.modules.appapi.entity.AppTeamPlayers;
import org.jeecg.modules.appapi.entity.vo.AppTeamPlayersVo;

import java.util.List;

/**
 * @Description: 球队表
 * @Author: jeecg-boot
 * @Date:   2020-07-04
 * @Version: V1.0
 */
public interface AppTeamPlayersService {
    //增 返回ID
    void insert(AppTeamPlayers record);
    //删 根据ID
    void deleteById(int id);
    //更新
    void update(AppTeamPlayers record);
    //根据ID查询
    AppTeamPlayers selectById(int id);

    List<AppTeamPlayersVo> selectByTeamId(int id,String playerName);

    void kickPlayer(Integer uid,Integer teamId);

    AppTeamPlayers selectByuId(Integer uId);

    void deleteByUId(Integer u_id);
}
