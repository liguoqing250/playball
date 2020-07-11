package org.jeecg.modules.appapi.service;

import org.jeecg.modules.appapi.entity.SportsType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 运动类型表
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface ISportsTypeService extends IService<SportsType> {
    List<SportsType> getSportsType();

    List<SportsType> querySportsTypeByBusinessId(String id);
}
