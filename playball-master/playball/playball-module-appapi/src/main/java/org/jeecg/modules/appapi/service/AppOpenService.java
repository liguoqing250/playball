package org.jeecg.modules.appapi.service;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface AppOpenService {
    //注册或登录
    public Map<String,Object> loginOrRegister(AppUsers appUsers,Boolean usePassworld);
}
