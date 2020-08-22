package org.jeecg.modules.appapi.service.impl;



import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.vo.AboutMe;
import org.jeecg.modules.appapi.mapper.AppUsersMapper;
import org.jeecg.modules.appapi.service.AppUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class AppUsersServiceImpl implements AppUsersService {

    @Autowired
    AppUsersMapper mapper;

    @Autowired
    private HttpSession session;

    @Override
    public void insert(AppUsers record) {
         mapper.insert(record);
    }
    @Override
    public void deleteById(Integer id) {
         mapper.deleteById(id);
    }

    @Override
    public AppUsers selectById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public void update(AppUsers record) {
        //获取当前用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token=request.getHeader("X-Access-Token");
        AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);

        record.setU_id(appUsers.getU_id());
        mapper.update(record);
    }
    @Override
    public List<AppUsers> selectByKey(Map<String,Object> map){
         return mapper.selectByKey(map);
    };

    @Override
    public Page selectByPage(Map<String,Object> map) {
        //Integer totalNum=mapper.getCount(map);
        Page page=new Page();
        return page;
    }

    @Override
    public AboutMe queryAboutMe(AboutMe aboutMe) {
        return mapper.queryAboutMe(aboutMe);
    }


}