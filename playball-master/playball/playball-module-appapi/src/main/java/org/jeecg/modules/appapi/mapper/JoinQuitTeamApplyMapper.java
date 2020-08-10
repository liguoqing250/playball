package org.jeecg.modules.appapi.mapper;

import org.jeecg.modules.appapi.entity.JoinQuitTeamApply;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.vo.JQTeamApplyVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface JoinQuitTeamApplyMapper {

    //增 返回ID
    void insert(JoinQuitTeamApply record);
    //删 根据ID
    void deleteById(Integer id);
    //更新
    void update(JoinQuitTeamApply record);
    //根据ID查询
    JoinQuitTeamApply selectById(Integer id);
    //多条件查询
    List<JoinQuitTeamApply> selectByKey(@Param("params") Map<String,Object> params);
    //多条件分页查询
    List<JoinQuitTeamApply> selectByPage(@Param("params") Map<String,Object> params);
    //获取总条数
    Integer getCount(@Param("params") Map<String,Object> params);


    List<JQTeamApplyVo> queryJoinQuitTeamApply(@Param("params") Map<String,Object> params);

    void deleteByTeamId(Integer team_id);

    void deleteByUId(Integer u_id);
}
