package org.jeecg.modules.playball.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.playball.entity.PlayballAdvertisement;
import org.jeecg.common.playball.entity.PlayballAdvertisementAppRoute;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 广告管理
 * @Author: jeecg-boot
 * @Date:   2020-08-01
 * @Version: V1.0
 */
public interface PlayballAdvertisementMapper extends BaseMapper<PlayballAdvertisement> {
	public List<PlayballAdvertisementAppRoute> getAppRouteList();
}
