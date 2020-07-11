package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules.appapi.entity.SportsType;
import org.jeecg.modules.appapi.mapper.SportsTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.jeecg.modules.appapi.service.ISportsTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 运动类型表
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Service
public class SportsTypeServiceImpl extends ServiceImpl<SportsTypeMapper, SportsType> implements ISportsTypeService {
    @Autowired
    SportsTypeMapper sportsTypeMapper;

    @Override
    public List<SportsType> getSportsType() {
        return sportsTypeMapper.getSportsType();
    }

    @Override
    public List<SportsType> querySportsTypeByBusinessId(String id) {
        return sportsTypeMapper.querySportsTypeByBusinessId(id);
    }
}
