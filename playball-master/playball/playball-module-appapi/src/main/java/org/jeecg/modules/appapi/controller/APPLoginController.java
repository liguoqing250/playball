package org.jeecg.modules.appapi.controller;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.encryption.AesEncryptUtil;
import org.jeecg.common.util.encryption.EncryptedString;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.service.AppOpenService;
import org.jeecg.modules.appapi.utils.SIMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/appopen")
@Slf4j
public class APPLoginController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    AppOpenService appOpenService;

    //注册
    @PostMapping(value = "/loginOrRegister")
    public Result<JSONObject> register(@RequestBody String json) {
        System.out.println(json);
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject jsonObject = JSONObject.parseObject(json);
        AppUsers appUsers= jsonObject.getObject("appUsers",AppUsers.class);
        boolean usePassworld= (boolean) jsonObject.get("usePassworld");
        try{
            //注册或登录
            Map<String,Object> map=appOpenService.loginOrRegister(appUsers,usePassworld);

            if((boolean)map.get("success")){
                appUsers= (AppUsers) map.get("appUsers");
                // 生成token
                String token =JwtUtil.appSign(JSONObject.toJSONString(appUsers),appUsers.getU_id().toString());
                // 设置token缓存有效时间
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME*2 / 1000);

                JSONObject obj = new JSONObject();
                obj.put("token", token);
                obj.put("userInfo", appUsers);
                result.setResult(obj);
                result.success(map.get("msg").toString());
            }else{
                result.error500(map.get("msg").toString());
            }
            System.out.println(map.get("msg").toString());
        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }

        return result;
    }

    //发送验证码
    @PostMapping(value = "/sendVerificationCode")
    public Result<JSONObject> sendVerificationCode(String phone) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            int code=(int)((Math.random()*9+1)*100000);
            System.out.println(phone);
            System.out.println(Integer.toString(code));
            SIMUtils.send(phone,Integer.toString(code));
            result.setMessage("发送成功");
            JSONObject obj = new JSONObject();
            obj.put("code", code);
            result.setResult(obj);
            result.setSuccess(false);
        }catch (Exception e){
           e.printStackTrace();
           result.setMessage("发送失败");
           result.setSuccess(false);
        }

        return result;
    }

}
