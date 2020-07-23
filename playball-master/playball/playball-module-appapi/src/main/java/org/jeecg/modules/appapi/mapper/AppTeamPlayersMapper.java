package org.jeecg.modules.appapi.mapper;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.AppTeamPlayers;
import org.jeecg.modules.appapi.entity.vo.AppTeamPlayersVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AppTeamPlayersMapper {
    //增 返回ID
    void insert(AppTeamPlayers record);
    //删 根据ID
    void deleteById(int id);
    //更新
    void update(AppTeamPlayers record);
    //根据ID查询
    AppTeamPlayers selectById(int id);
    //根据注册方式查询
    AppTeamPlayers selectMyRegisterWay(AppTeamPlayers record);
    //多条件查询
    List<AppTeamPlayers> selectByPage(@Param("params") Map<String, Object> params);
    //多条件分页查询
    List<AppTeamPlayers> selectByKey(@Param("params") Map<String, Object> params);
    //获取总条数
    int getCount(@Param("params") Map<String, Object> params);

    AppTeamPlayers selectByUserId(int userId);

    List<AppTeamPlayersVo> selectByTeamId(@Param("params") Map<String, Object> params);

    void kickPlayer(AppTeamPlayers appTeamPlayers);

    void removePlayer(Integer team_id);
}
