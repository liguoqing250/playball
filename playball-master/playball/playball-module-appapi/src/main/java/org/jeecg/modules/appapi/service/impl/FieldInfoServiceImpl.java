package org.jeecg.modules.appapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.FieldInfo;
import org.jeecg.modules.appapi.mapper.FieldInfoMapper;
import org.jeecg.modules.appapi.service.IFieldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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

    @Override
    public List<FieldInfo> queryByMyOrders() {
        //获取当前用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token=request.getHeader("X-Access-Token");
        AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
        return fieldInfoMapper.queryByMyOrders(appUsers.getU_id());
    }
}
