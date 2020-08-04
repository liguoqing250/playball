package org.jeecg.modules.appapi.service;

import org.jeecg.modules.appapi.entity.SportsData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 球员能力值
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
public interface ISportsDataService extends IService<SportsData> {

    List<SportsData> queryByStId(Integer stId);
}
