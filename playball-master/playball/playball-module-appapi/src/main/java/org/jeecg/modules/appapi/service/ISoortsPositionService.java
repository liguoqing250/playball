package org.jeecg.modules.appapi.service;

import org.jeecg.modules.appapi.entity.SoortsPosition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 运动员位置
 * @Author: jeecg-boot
 * @Date:   2020-07-21
 * @Version: V1.0
 */
public interface ISoortsPositionService extends IService<SoortsPosition> {
    List<SoortsPosition> queryByStId(Integer stId);
}
