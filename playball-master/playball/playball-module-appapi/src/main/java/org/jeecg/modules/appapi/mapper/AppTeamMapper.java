package org.jeecg.modules.appapi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.entity.vo.TeamScoreInfoVo;
import org.springframework.stereotype.Repository;

@Repository
public interface AppTeamMapper {
    //增 返回ID
    void insert(AppTeam record);
    //删 根据ID
    void deleteById(int id);
    //更新
    void update(AppTeam record);
    //根据ID查询
    AppTeam selectById(int id);
    //根据注册方式查询
    AppTeam selectMyRegisterWay(AppTeam record);
    //多条件查询
    List<AppTeam> selectByPage(@Param("params") Map<String, Object> params);
    //多条件分页查询
    List<AppTeam> selectByKey(@Param("params") Map<String, Object> params);
    //获取总条数
    int getCount(@Param("params") Map<String, Object> params);

    AppTeam isCaptain(Integer u_id);

    List<AppTeam> queryTeamByEnroll(Integer id);

    List<AppTeam> queryTeamByGameid(Integer id);

    List<AppTeam> querTeamByMyApply(Integer uId);

    TeamScoreInfoVo selectTeamScoreInfoById(Integer id);
}
