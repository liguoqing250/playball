package org.jeecg.modules.appapi.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.common.util.encryption.AesEncryptUtil;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.vo.AboutMe;
import org.jeecg.modules.appapi.mapper.AppUsersMapper;
import org.jeecg.modules.appapi.service.AppOpenService;
import org.jeecg.modules.appapi.service.AppUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appUsers")
@Slf4j
public class APPUserController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    AppUsersService appUsersService;
    @Autowired
    AppUsersMapper appUsersMapper;
    //更新
    @PostMapping(value = "/updateMyPassWorld")
    public Result<JSONObject> updateMyPassWorld(String newPassword,HttpServletRequest request) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            String token=request.getHeader("X-Access-Token");
            AppUsers appUsers=JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
            AppUsers newUsers=new AppUsers();
            newUsers.setU_id(appUsers.getU_id());
            newUsers.setU_passworld(AesEncryptUtil.encrypt(newPassword));
            appUsersMapper.update(newUsers);
            result.success("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }
        return result;
    }
    @PostMapping(value = "/chickCode")
    public Result<JSONObject> chickCode(String phoneNumber,int code) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            if(redisUtil.hasKey(phoneNumber)){
                if(code==(int)redisUtil.get(phoneNumber)){
                    result.setSuccess(true);
                }else{
                    result.error500("验证码错误");
                }
            }else{
                result.error500("验证码不存在");
            }

        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }
        return result;
    }
    @PostMapping(value = "/bindingPhone")
    public Result<JSONObject> bindingPhone(String phoneNumber,int code) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            if(redisUtil.hasKey(phoneNumber)){
                System.out.println(code);
                System.out.println((int)redisUtil.get(phoneNumber));
                System.out.println(code==(int)redisUtil.get(phoneNumber));
                if(code==(int)redisUtil.get(phoneNumber)){
                    Map<String,Object> map=new HashedMap();
                    map.put("u_phoneNumber",phoneNumber);
                    List<AppUsers> list=appUsersService.selectByKey(map);
                    if(list.size()>0){
                        result.error500("该手机号已绑定,请勿重复绑定!");
                    }else{
                        AppUsers appUsers=new AppUsers();
                        appUsers.setU_phoneNumber(phoneNumber);
                        appUsersService.update(appUsers);
                        result.success("绑定成功");
                    }
                }else{
                    result.error500("验证码错误");
                }

            }else{
                result.error500("验证码不存在");
            }

        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }
        return result;
    }
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
    //根据id查询
    @PostMapping(value = "/CheckLoginStatus")
    public boolean CheckLoginStatus() {
        return true;
    }
    //根据id查询
    @PostMapping(value = "/getUserInfoById")
    public Result<JSONObject> getMyUserInfo(int id) {
        Result<JSONObject> result = new Result<JSONObject>();

        try{
            JSONObject obj = new JSONObject();
            obj.put("data", appUsersService.selectById(id));
            result.setResult(obj);
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
    //获取我的个人信息
    @PostMapping(value = "/queryAboutMe")
    public Result<?> queryAboutMe(AboutMe aboutMe) {

        try{
           return Result.ok(appUsersService.queryAboutMe(aboutMe));
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("请求失败");
        }
    }
    //获取我关注的
    @PostMapping(value = "/queryMyFllow")
    public Result<?> queryMyFllow(Integer uId) {

        try{
            return Result.ok(appUsersMapper.queryMyFllow(uId));
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("请求失败");
        }
    }
    //获取关注我的
    @PostMapping(value = "/queryFllowMe")
    public Result<?> queryFllowMe(Integer uId) {

        try{
            return Result.ok(appUsersMapper.queryFllowMe(uId));
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("请求失败");
        }
    }
}
