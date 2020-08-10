package org.jeecg.modules.appapi.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.vo.AboutMe;

import java.util.List;
import java.util.Map;

public interface AppUsersService {

    //增 返回ID
    void insert(AppUsers record);
    //删 根据ID
    void deleteById(Integer id);
    //更新
    void update(AppUsers record);
    //根据ID查询
    AppUsers selectById(Integer id);
    //多条件查询
    List<AppUsers> selectByKey(Map<String,Object> params);
    //多条件分页查询
    Page selectByPage(Map<String,Object> params);

    AboutMe queryAboutMe(AboutMe u_id);
}
