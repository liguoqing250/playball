package org.jeecg.modules.appapi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.ScheduleUserdata;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.appapi.entity.vo.PlayerScoreInfo;

/**
 * @Description: 赛程球员数据
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
public interface ScheduleUserdataMapper extends BaseMapper<ScheduleUserdata> {

    List<PlayerScoreInfo> queryPlayerInfoByTpId(Integer tpId);
}
