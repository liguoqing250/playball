package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules.appapi.entity.ScheduleUserdata;
import org.jeecg.modules.appapi.entity.vo.PlayerScoreInfo;
import org.jeecg.modules.appapi.mapper.ScheduleUserdataMapper;
import org.jeecg.modules.appapi.service.IScheduleUserdataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

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
}
