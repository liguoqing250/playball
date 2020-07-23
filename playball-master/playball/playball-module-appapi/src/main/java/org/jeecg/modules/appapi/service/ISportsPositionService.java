package org.jeecg.modules.appapi.service;

import org.jeecg.modules.appapi.entity.SportsPosition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 运动员位置
 * @Author: jeecg-boot
 * @Date:   2020-07-21
 * @Version: V1.0
 */
public interface ISportsPositionService extends IService<SportsPosition> {
    List<SportsPosition> queryByStId(Integer stId);
}
