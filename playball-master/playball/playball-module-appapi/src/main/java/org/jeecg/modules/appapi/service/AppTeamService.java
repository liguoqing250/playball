package org.jeecg.modules.appapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.AppTeam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.appapi.entity.AppUsers;

import java.util.List;
import java.util.Map;

/**
 * @Description: 球队表
 * @Author: jeecg-boot
 * @Date:   2020-07-04
 * @Version: V1.0
 */
public interface AppTeamService  {
    void createTeam(AppTeam record);
    //增 返回ID
    void insert(AppTeam record);
    //删 根据ID
    void deleteById(int id);
    //更新
    void update(AppTeam record);
    //根据ID查询
    AppTeam selectById(int id);

    boolean isJoinTeam();
    AppTeam selectMyTeamInfo();

    Page<AppTeam> selectByPage(Map<String ,Object> params);

    boolean isCaptain();

    List<AppTeam> queryTeamByGameid(Integer id);

    List<AppTeam> queryTeamByEnroll(Integer id);
}
