package org.jeecg.modules.appapi.service;

import org.jeecg.modules.appapi.entity.ScheduleUserdata;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.appapi.entity.vo.PlayerScoreInfo;

import java.util.List;

/**
 * @Description: 赛程球员数据
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
public interface IScheduleUserdataService extends IService<ScheduleUserdata> {

    List<PlayerScoreInfo> queryPlayerInfoByTpId(Integer tpId);
}
