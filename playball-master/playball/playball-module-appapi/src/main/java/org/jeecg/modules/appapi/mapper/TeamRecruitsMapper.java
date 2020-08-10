package org.jeecg.modules.appapi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.entity.TeamRecruits;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 招募信息
 * @Author: jeecg-boot
 * @Date:   2020-07-21
 * @Version: V1.0
 */
public interface TeamRecruitsMapper extends BaseMapper<TeamRecruits> {

    List<TeamRecruits> selectByPage(@Param("params")Map<String, Object> params);

    int getCount(@Param("params") Map<String, Object> params);

    void removeRecruitsByTeam(Integer team_id);

    void deleteByTeamId(Integer team_id);
}
