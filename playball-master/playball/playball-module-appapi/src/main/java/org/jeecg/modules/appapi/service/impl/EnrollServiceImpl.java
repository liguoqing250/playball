package org.jeecg.modules.appapi.service.impl;

import org.apache.commons.collections.map.HashedMap;
import org.jeecg.modules.appapi.entity.Enroll;
import org.jeecg.modules.appapi.mapper.EnrollMapper;
import org.jeecg.modules.appapi.service.IEnrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 比赛报名表
 * @Author: jeecg-boot
 * @Date:   2020-07-11
 * @Version: V1.0
 */
@Service
public class EnrollServiceImpl extends ServiceImpl<EnrollMapper, Enroll> implements IEnrollService {
    @Autowired
    EnrollMapper enrollMapper;

    @Override
    public boolean hasenroll(Enroll enroll) {
        Map<String,Object>map=new HashedMap();
        map.put("team_id",enroll.getTeamId());
        map.put("games_id",enroll.getGamesId());
        List<Enroll> select=enrollMapper.selectByMap(map);
        if(select.size()>0){
            //没有参加了
            return true;
        }else{
            //没有参加
            return false;
        }

    }
}
