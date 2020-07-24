package org.jeecg.modules.playball.service.impl;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballScheduleUserData;
import org.jeecg.modules.playball.mapper.PlayballScheduleUserDataMapper;
import org.jeecg.modules.playball.service.IPlayballScheduleUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 球员数据统计
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Service
public class PlayballScheduleUserDataServiceImpl extends ServiceImpl<PlayballScheduleUserDataMapper, PlayballScheduleUserData> implements IPlayballScheduleUserDataService {
	@Autowired
	private PlayballScheduleUserDataMapper userDataMapper;
	
	@Override
	public List<PlayballScheduleUserData> getListBySportsId(Integer sportsId){
		return userDataMapper.getListBySportsId(sportsId);
	}
}
