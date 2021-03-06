package org.jeecg.modules.appapi.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.UserNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.appapi.entity.vo.UserNoticeVo;

/**
 * @Description: 消息
 * @Author: jeecg-boot
 * @Date:   2020-08-17
 * @Version: V1.0
 */
public interface UserNoticeMapper extends BaseMapper<UserNotice> {

    Integer getCountNotRead(UserNoticeVo userNoticeVo);
}
