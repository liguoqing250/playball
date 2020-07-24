package org.jeecg.modules.playball.service.impl;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballAbilityValue;
import org.jeecg.modules.playball.mapper.PlayballAbilityValueMapper;
import org.jeecg.modules.playball.service.IPlayballAbilityValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 能力值
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Service
public class PlayballAbilityValueServiceImpl extends ServiceImpl<PlayballAbilityValueMapper, PlayballAbilityValue> implements IPlayballAbilityValueService {
	
	@Autowired
	private PlayballAbilityValueMapper abilityMapper;
	
	@Override
	public List<PlayballAbilityValue> getListBySportsId(Integer sportsId){
		return abilityMapper.getListBySportsId(sportsId);
	}
}
