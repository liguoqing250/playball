package org.jeecg.modules.appapi.service.impl;

import org.jeecg.modules.appapi.entity.UserFollow;
import org.jeecg.modules.appapi.mapper.UserFollowMapper;
import org.jeecg.modules.appapi.service.IUserFollowService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 用户关注表
 * @Author: jeecg-boot
 * @Date:   2020-08-12
 * @Version: V1.0
 */
@Service
public class UserFollowServiceImpl extends ServiceImpl<UserFollowMapper, UserFollow> implements IUserFollowService {

}
