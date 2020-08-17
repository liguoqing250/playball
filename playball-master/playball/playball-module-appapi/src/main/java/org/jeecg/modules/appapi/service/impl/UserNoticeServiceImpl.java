package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules.appapi.entity.UserNotice;
import org.jeecg.modules.appapi.mapper.UserNoticeMapper;
import org.jeecg.modules.appapi.service.IUserNoticeService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 消息
 * @Author: jeecg-boot
 * @Date:   2020-08-17
 * @Version: V1.0
 */
@Service
public class UserNoticeServiceImpl extends ServiceImpl<UserNoticeMapper, UserNotice> implements IUserNoticeService {

}
