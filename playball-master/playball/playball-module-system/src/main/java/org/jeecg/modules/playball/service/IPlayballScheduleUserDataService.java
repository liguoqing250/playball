package org.jeecg.modules.playball.service;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballScheduleUserData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 球员数据统计
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface IPlayballScheduleUserDataService extends IService<PlayballScheduleUserData> {
	
	public List<PlayballScheduleUserData> getListBySportsId(String sportsId);
	
}
