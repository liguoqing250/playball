package org.jeecg.modules.playball.service;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballAbilityValue;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 能力值
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
public interface IPlayballAbilityValueService extends IService<PlayballAbilityValue> {
	
	public List<PlayballAbilityValue> getListBySportsId(String sportsId);
	
}
