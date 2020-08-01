package org.jeecg.modules.playball.service;

import java.util.List;

import org.jeecg.common.playball.entity.PlayballAdvertisement;
import org.jeecg.common.playball.entity.PlayballAdvertisementAppRoute;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 广告管理
 * @Author: jeecg-boot
 * @Date:   2020-08-01
 * @Version: V1.0
 */
public interface IPlayballAdvertisementService extends IService<PlayballAdvertisement> {
	public List<PlayballAdvertisementAppRoute> getAppRouteList();
}
