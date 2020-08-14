package org.jeecg.modules.appapi.service.impl;

import org.apache.commons.collections.map.HashedMap;
import org.jeecg.modules.appapi.entity.ScheduleUserdata;
import org.jeecg.modules.appapi.entity.bo.TeamRankBo;
import org.jeecg.modules.appapi.entity.vo.PlayerScoreInfo;
import org.jeecg.modules.appapi.mapper.ScheduleUserdataMapper;
import org.jeecg.modules.appapi.service.IScheduleUserdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 赛程球员数据
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
@Service
public class ScheduleUserdataServiceImpl extends ServiceImpl<ScheduleUserdataMapper, ScheduleUserdata> implements IScheduleUserdataService {
    @Autowired
    ScheduleUserdataMapper scheduleUserdataMapper;
    @Override
    public List<PlayerScoreInfo> queryPlayerInfoByTpId(Integer tpId) {
        return scheduleUserdataMapper.queryPlayerInfoByTpId(tpId);
    }

    @Override
    public List<ScheduleUserdata> queryPlayerGameDataByTeam(Integer team_id, Integer schedule_id) {
        Map<String,Object> map =new HashedMap();
        map.put("team_id",team_id);
        map.put("schedule_id",schedule_id);
        return scheduleUserdataMapper.queryPlayerGameDataByTeam(map);
    }

    @Override
    public List<ScheduleUserdata> queryAll() {
        return scheduleUserdataMapper.queryAll();
    }

    @Override
    public List<TeamRankBo> queryAllAsTeam() {
        return scheduleUserdataMapper.queryAllAsTeam();
    }
}
