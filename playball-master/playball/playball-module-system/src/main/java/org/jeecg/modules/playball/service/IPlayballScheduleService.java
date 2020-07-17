package org.jeecg.modules.playball.service;

import org.jeecg.modules.playball.entity.PlayballSchedule;
import org.jeecg.modules.playball.vo.PlayballScheduleInfoPage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 赛程表
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
public interface IPlayballScheduleService extends IService<PlayballSchedule> {
	
	//获取比赛列表
	public IPage<PlayballScheduleInfoPage>  querySchedulePageList(IPage page, PlayballScheduleInfoPage scheduleInfo);
	
}
