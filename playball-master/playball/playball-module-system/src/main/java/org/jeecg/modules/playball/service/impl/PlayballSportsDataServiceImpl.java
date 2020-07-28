package org.jeecg.modules.playball.service.impl;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballAbilityValue;
import org.jeecg.common.playball.entity.PlayballSportsData;
import org.jeecg.modules.playball.mapper.PlayballSportsDataMapper;
import org.jeecg.modules.playball.service.IPlayballSportsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 运动数据类型
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Service
public class PlayballSportsDataServiceImpl extends ServiceImpl<PlayballSportsDataMapper, PlayballSportsData> implements IPlayballSportsDataService {
	
	@Autowired
	private PlayballSportsDataMapper dataMapper;
	
	@Override
	public List<PlayballSportsData> getListBySportsId(String sportsId){
		if(sportsId == null) {
			return this.list();
		}else {
			return dataMapper.getListBySportsId(Integer.valueOf(sportsId));
		}
		
	}
}
