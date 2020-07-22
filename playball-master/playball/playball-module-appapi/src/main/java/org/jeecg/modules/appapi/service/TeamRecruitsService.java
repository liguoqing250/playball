package org.jeecg.modules.appapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.entity.TeamRecruits;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Description: 招募信息
 * @Author: jeecg-boot
 * @Date:   2020-07-21
 * @Version: V1.0
 */
public interface TeamRecruitsService extends IService<TeamRecruits> {

    Page<TeamRecruits> selectByPage(Map<String, Object> params);
}
