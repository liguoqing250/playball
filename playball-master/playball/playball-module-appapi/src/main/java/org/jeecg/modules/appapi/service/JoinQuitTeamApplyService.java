package org.jeecg.modules.appapi.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.entity.JoinQuitTeamApply;
import org.jeecg.modules.appapi.entity.vo.JQTeamApplyVo;

import java.util.List;
import java.util.Map;

public interface JoinQuitTeamApplyService {

    //增 返回ID
    JSONObject joinTeamApply(JoinQuitTeamApply record);
    //删 根据ID
    void deleteById(Integer id);
    //更新
    void update(JoinQuitTeamApply record);
    //根据ID查询
    JoinQuitTeamApply selectById(Integer id);
    //多条件查询
    List<JoinQuitTeamApply> selectByKey(Map<String,Object> params);
    //多条件分页查询
    Page selectByPage(Map<String,Object> params);

    List<JQTeamApplyVo> queryJoinQuitTeamApply(Integer jqtaType);
}
