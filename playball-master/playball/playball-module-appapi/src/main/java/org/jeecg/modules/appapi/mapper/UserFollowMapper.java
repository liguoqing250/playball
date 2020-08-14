package org.jeecg.modules.appapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.UserFollow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 用户关注表
 * @Author: jeecg-boot
 * @Date:   2020-08-12
 * @Version: V1.0
 */
public interface UserFollowMapper extends BaseMapper<UserFollow> {

    UserFollow isFllow(UserFollow userFollow);
}
