package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules.appapi.entity.FieldInfo;
import org.jeecg.modules.appapi.mapper.FieldInfoMapper;
import org.jeecg.modules.appapi.service.IFieldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * @Description: 场馆详情
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Service
public class FieldInfoServiceImpl extends ServiceImpl<FieldInfoMapper, FieldInfo> implements IFieldInfoService {

    @Autowired
    FieldInfoMapper fieldInfoMapper;

    @Override
    public List<FieldInfo> listMaps(Map<String, Object> map) {
        return fieldInfoMapper.selectByMap(map);
    }
}
