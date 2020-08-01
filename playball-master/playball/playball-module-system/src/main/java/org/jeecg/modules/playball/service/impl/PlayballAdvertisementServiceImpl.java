package org.jeecg.modules.playball.service.impl;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballAdvertisement;
import org.jeecg.common.playball.entity.PlayballAdvertisementAppRoute;
import org.jeecg.modules.playball.mapper.PlayballAdvertisementMapper;
import org.jeecg.modules.playball.service.IPlayballAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 广告管理
 * @Author: jeecg-boot
 * @Date:   2020-08-01
 * @Version: V1.0
 */
@Service
public class PlayballAdvertisementServiceImpl extends ServiceImpl<PlayballAdvertisementMapper, PlayballAdvertisement> implements IPlayballAdvertisementService {
	@Autowired
	private PlayballAdvertisementMapper advertisementMapper;
	
	@Override
	public List<PlayballAdvertisementAppRoute> getAppRouteList() {
		return advertisementMapper.getAppRouteList();
	}
}
