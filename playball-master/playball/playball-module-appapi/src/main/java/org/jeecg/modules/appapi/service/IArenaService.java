package org.jeecg.modules.appapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.appapi.entity.Arena;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Description: 商家表
 * @Author: jeecg-boot
 * @Date:   2020-07-09
 * @Version: V1.0
 */
public interface IArenaService extends IService<Arena> {

    Page<Arena> queryPageList(Page<Arena> page, Map<String, String[]> parameterMap);
}
