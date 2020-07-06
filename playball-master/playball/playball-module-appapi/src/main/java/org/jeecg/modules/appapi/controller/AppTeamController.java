package org.jeecg.modules.appapi.controller;

import cn.hutool.db.PageResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.service.AppTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/appTeam")
@Slf4j
public class AppTeamController {
    @Autowired
    AppTeamService appTeamService;
    //创建球队
    @PostMapping(value = "/createTeam")
    public Result<JSONObject> register(@RequestBody AppTeam appTeam) {
        System.out.println(appTeam.getT_name());
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            //appTeamService.insert(appTeam);
            appTeamService.createTeam(appTeam);
            JSONObject obj = new JSONObject();
            obj.put("data",appTeam);
            result.success("创建成功");
            result.setResult(obj);
        }catch (Exception e){
            e.printStackTrace();
            result.error500("创建失败");
        }
        return result;
    }
    //判断是否已加入球队
    @PostMapping(value = "/isJoinTeam")
    public Result<JSONObject> isJoinTeam() {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            JSONObject obj = new JSONObject();
            obj.put("isJoinTeam",appTeamService.isJoinTeam());
            result.setResult(obj);
            result.success("请求成功");
        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }
        return result;
    }
    //获取当前用户所属球队信息
    @PostMapping(value = "/getMyTeamInfo")
    public Result<JSONObject> getMyTeamInfo() {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            JSONObject obj = new JSONObject();
            obj.put("data",appTeamService.selectMyTeamInfo());
            result.setResult(obj);
            result.success("请求成功");
        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }
        return result;
    }
    //分页获取球队信息
    @PostMapping(value = "/selectTeamByPage")
    public Result<JSONObject> selectTeamByPage(@RequestBody Map<String,Object> params) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            JSONObject obj = new JSONObject();
            Page<AppTeam> page=appTeamService.selectByPage(params);
            obj.put("page",page);
            result.setResult(obj);
            result.success("请求成功");
        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }
        return result;
    }
}
