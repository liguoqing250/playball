package org.jeecg.modules.appapi.service;

import org.jeecg.modules. appapi.entity.Game;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 比赛
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
public interface IGameService extends IService<Game> {
    List<Game> queryByFieldId(Integer id);
}
