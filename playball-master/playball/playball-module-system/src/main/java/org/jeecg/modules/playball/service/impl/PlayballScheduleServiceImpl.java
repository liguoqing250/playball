package org.jeecg.modules.playball.service.impl;

import java.util.List;
import java.util.Map;

import org.jeecg.modules.playball.entity.PlayballSchedule;
import org.jeecg.modules.playball.mapper.PlayballScheduleMapper;
import org.jeecg.modules.playball.service.IPlayballScheduleService;
import org.jeecg.modules.playball.vo.PlayballScheduleInfoPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 赛程表
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
@Service
public class PlayballScheduleServiceImpl extends ServiceImpl<PlayballScheduleMapper, PlayballSchedule> implements IPlayballScheduleService {
	
	@Autowired
	private PlayballScheduleMapper scheduleMapper;
	
	@Override
	public IPage<PlayballScheduleInfoPage>  querySchedulePageList(IPage page, PlayballScheduleInfoPage scheduleInfo){
		
		List<PlayballScheduleInfoPage>  list = scheduleMapper.getSchedulePageList(page, scheduleInfo);
		Integer total = scheduleMapper.getSchedulePageListTotle(scheduleInfo);
		IPage<PlayballScheduleInfoPage> result = new Page<>(page.getCurrent(), page.getSize(), total);
		
		result.setRecords(list);
		return result;
	}
}
