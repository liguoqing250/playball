package org.jeecg.modules.appapi.service.impl;

import org.apache.commons.collections.map.HashedMap;
import org.jeecg.modules.appapi.entity.SportsPosition;
import org.jeecg.modules.appapi.mapper.SportsPositionMapper;
import org.jeecg.modules.appapi.service.ISportsPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 运动员位置
 * @Author: jeecg-boot
 * @Date:   2020-07-21
 * @Version: V1.0
 */
@Service
public class SportsPositionServiceImpl extends ServiceImpl<SportsPositionMapper, SportsPosition> implements ISportsPositionService {
    @Autowired
    SportsPositionMapper sportsPositionMapper;
    @Override
    public List<SportsPosition> queryByStId(Integer stId) {
        Map<String,Object> map =new HashedMap();
        map.put("st_id",stId);
        return sportsPositionMapper.selectByMap(map);
    }
}
