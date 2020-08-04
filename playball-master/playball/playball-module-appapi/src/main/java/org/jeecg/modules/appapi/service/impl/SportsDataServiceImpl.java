package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules.appapi.entity.SportsData;
import org.jeecg.modules.appapi.mapper.SportsDataMapper;
import org.jeecg.modules.appapi.service.ISportsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 球员能力值
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
@Service
public class SportsDataServiceImpl extends ServiceImpl<SportsDataMapper, SportsData> implements ISportsDataService {
    @Autowired
    SportsDataMapper sportsDataMapper;

    @Override
    public List<SportsData> queryByStId(Integer stId) {
        Map<String,Object> map=new HashMap();
        map.put("sports_id",stId);
        return sportsDataMapper.selectByMap(map);
    }
}
