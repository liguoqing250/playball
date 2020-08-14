package org.jeecg.modules.appapi.mapper;


import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.vo.AboutMe;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AppUsersMapper {
    //增 返回ID
    void insert(AppUsers record);
    //删 根据ID
    void deleteById(int id);
    //更新
    void update(AppUsers record);
    //根据ID查询
    AppUsers selectById(int id);
    //根据注册方式查询
    AppUsers selectMyRegisterWay(AppUsers record);
    //多条件查询
    List<AppUsers> selectByPage(@Param("params") Map<String, Object> params);
    //多条件分页查询
    List<AppUsers> selectByKey(@Param("params") Map<String, Object> params);
    //获取总条数
    int getCount(@Param("params") Map<String, Object> params);

    AboutMe queryAboutMe(AboutMe aboutMe);

    //查询我关注的
    List<AppUsers> queryMyFllow(Integer uId);
    //查询关注我的
    List<AppUsers> queryFllowMe(Integer uId);
}