package org.jeecg.modules.appapi.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.service.AppOpenService;
import org.jeecg.modules.appapi.service.AppUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/appUsers")
@Slf4j
public class APPUserController {
    @Autowired
    AppUsersService appUsersService;
    //更新
    @PostMapping(value = "/updateMyUserInfo")
    public Result<JSONObject> updateMyUserInfo(@RequestBody AppUsers appUsers) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            JSONObject obj = new JSONObject();
            appUsersService.update(appUsers);
            result.success("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }

        return result;
    }
    //获取我的个人信息
    @PostMapping(value = "/getMyUserInfo")
    public Result<JSONObject> getMyUserInfo(HttpServletRequest request) {
        Result<JSONObject> result = new Result<JSONObject>();

        try{
            JSONObject obj = new JSONObject();
            String token=request.getHeader("X-Access-Token");
            AppUsers appUsers=JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
            obj.put("data", appUsersService.selectById(appUsers.getU_id()));
            result.setResult(obj);
        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }

        return result;
    }
}
