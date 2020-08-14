package org.jeecg.modules.appapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.entity.AppTeamPlayers;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.JoinQuitTeamApply;
import org.jeecg.modules.appapi.entity.vo.AppTeamPlayersVo;
import org.jeecg.modules.appapi.entity.vo.JQTeamApplyVo;
import org.jeecg.modules.appapi.mapper.AppTeamMapper;
import org.jeecg.modules.appapi.service.AppTeamPlayersService;
import org.jeecg.modules.appapi.service.AppTeamService;
import org.jeecg.modules.appapi.service.JoinQuitTeamApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appTeam")
@Slf4j
public class AppTeamController {

    @Autowired
    AppTeamMapper appTeamMapper;
    @Autowired
    AppTeamService appTeamService;
    @Autowired
    AppTeamPlayersService appTeamPlayersService;
    @Autowired
    JoinQuitTeamApplyService joinQuitTeamApplyService;
    //更具uid查询球队
    @PostMapping(value = "/getTeamByUid")
    public Result<?> getTeamByUid(Integer uId) {
        try{
           AppTeamPlayers appTeamPlayers= appTeamPlayersService.selectByuId(uId);
           if(appTeamPlayers!=null){
               return Result.ok(appTeamService.selectById(appTeamPlayers.getTeam_id()));
           }else{
               return Result.error("没有数据");
           }
        }catch (Exception e){
            e.printStackTrace();
            return  Result.error("查询失败");
        }
    }

    //个人退队
    @PostMapping(value = "/quitTeam")
    public Result<?> quitTeam() {
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token=request.getHeader("X-Access-Token");
            AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
            AppTeamPlayers appTeamPlayers= appTeamPlayersService.selectByuId(appUsers.getU_id());
            AppTeam appTeam=appTeamService.selectById(appTeamPlayers.getTeam_id());
            appTeam.setTeam_id(appTeamPlayers.getTeam_id());
            appTeam.setT_players_total(appTeam.getT_players_total()-1);
            appTeamService.update(appTeam);
            appTeamPlayersService.deleteById(appTeamPlayers.getTp_id());
            return Result.ok("退队成功");
        }catch (Exception e){
            e.printStackTrace();
            return  Result.error("查询失败");
        }
    }
    //根据uid查询球员信息
    @PostMapping(value = "/selectTeamPlayerByUId")
    public Result<?> selectTeamPlayerByUId(@RequestParam Integer uId) {
        try{
            return Result.ok(appTeamPlayersService.selectByuId(uId));
        }catch (Exception e){
            e.printStackTrace();
            return  Result.error("查询失败");
        }
    }
    //查询入队、退队申请
    @PostMapping(value = "/selectTeamPlayerByTpId")
    public Result<?> selectTeamPlayerByTpId(@RequestParam Integer tpId) {
        try{
            return Result.ok(appTeamPlayersService.selectById(tpId));
        }catch (Exception e){
            e.printStackTrace();
            return  Result.error("查询失败");
        }
    }
    //解散球队
    @PostMapping(value = "/disbandTeam")
    public Result<JSONObject> disbandTeam() {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
           appTeamService.disbandTeam();
            result.success("操作完成");
        }catch (Exception e){
            e.printStackTrace();
            result.error500("操作失败");
        }
        return result;
    }
    //查询入队、退队申请
    @PostMapping(value = "/queryJoinQuitTeamApply")
    public Result<JSONObject> queryJoinQuitTeamApply( Integer jqtaType, String playerName) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            List<JQTeamApplyVo> list=joinQuitTeamApplyService.queryJoinQuitTeamApply(jqtaType,playerName);
            JSONObject obj = new JSONObject();
            obj.put("data",list);
            result.success("查询成功");
            result.setResult(obj);
        }catch (Exception e){
            e.printStackTrace();
            result.error500("查询失败");
        }
        return result;
    }
    //根据比赛查赛程
    @PostMapping(value = "/queryTeamByGame")
    public Result<JSONObject> queryTeamByGameid(Integer id) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            List<AppTeam> list=appTeamService.queryTeamByGameid(id);
            JSONObject obj = new JSONObject();
            obj.put("data",list);
            result.success("查询成功");
            result.setResult(obj);
        }catch (Exception e){
            e.printStackTrace();
            result.error500("查询失败");
        }
        return result;
    }
    //update
    @PostMapping(value = "/update")
    public Result<JSONObject> update(@RequestBody AppTeam appTeam) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            appTeamService.update(appTeam);
            result.success("编辑成功");
        }catch (Exception e){
            e.printStackTrace();
            result.error500("编辑失败");
        }
        return result;
    }
    //根据比赛查报名
    @PostMapping(value = "/queryTeamByEnroll")
    public Result<JSONObject> queryTeamByEnroll(Integer id) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            List<AppTeam> list=appTeamService.queryTeamByEnroll(id);
            JSONObject obj = new JSONObject();
            obj.put("data",list);
            result.success("查询成功");
            result.setResult(obj);
        }catch (Exception e){
            e.printStackTrace();
            result.error500("查询失败");
        }
        return result;
    }
    //更新
    @PostMapping(value = "/updateJoinTeamApply")
    public Result<?> updateJoinTeamApply(@RequestBody JoinQuitTeamApply joinQuitTeamApply) {
        try{
            AppTeamPlayers atp=appTeamPlayersService.selectByuId(joinQuitTeamApply.getU_id());
            if(atp!=null){
                return Result.ok("该用户已加入其他球队");
            }
            joinQuitTeamApply.setJqta_handleTime(new Date());
            joinQuitTeamApplyService.update(joinQuitTeamApply);
            if(joinQuitTeamApply.getJqta_result()==1){
                //审核通过
                //更新球队数据
                AppTeam appTeam=appTeamService.selectById(joinQuitTeamApply.getTeam_id());
                appTeam.setT_players_total(appTeam.getT_players_total()+1);
                appTeamService.update(appTeam);
                //添加球员表
                AppTeamPlayers appTeamPlayers=new AppTeamPlayers();
                appTeamPlayers.setU_id(joinQuitTeamApply.getU_id());
                appTeamPlayers.setTeam_id(joinQuitTeamApply.getTeam_id());
                appTeamPlayers.setTp_joinTime(new Date());
                appTeamPlayers.setTp_position(joinQuitTeamApply.getPosition());
                appTeamPlayersService.insert(appTeamPlayers);
            }
            return Result.ok("操作成功");
        }catch (Exception e){
            e.printStackTrace();
            return Result.ok("操作失败");
        }
    }
    //申请加入球队
    @PostMapping(value = "/joinTeamApply")
    public Result<JSONObject> joinTeamApply(@RequestBody JoinQuitTeamApply joinQuitTeamApply) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            JSONObject obj = joinQuitTeamApplyService.joinTeamApply(joinQuitTeamApply);
            result.success(obj.get("msg").toString());
            result.setResult(obj);
        }catch (Exception e){
            e.printStackTrace();
            result.error500("申请失败");
        }
        return result;
    }
    @PostMapping(value = "/selectTeamScoreInfoById")
    public Result<JSONObject> selectTeamScoreInfoById(Integer id) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            JSONObject obj = new JSONObject();
            obj.put("data", appTeamService.selectTeamScoreInfoById(id));
            result.success("查询成功");
            result.setResult(obj);
        }catch (Exception e){
            e.printStackTrace();
            result.error500("查询失败");
        }
        return result;
    }
    @PostMapping(value = "/selectTeamById")
    public Result<JSONObject> selectTeamById(@RequestBody AppTeam appTeam) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            JSONObject obj = new JSONObject();
            obj.put("data", appTeamService.selectById(appTeam.getTeam_id()));
            result.success("查询成功");
            result.setResult(obj);
        }catch (Exception e){
            e.printStackTrace();
            result.error500("查询失败");
        }
        return result;
    }
    //创建球队
    @PostMapping(value = "/createTeam")
    public Result<JSONObject> createTeam(@RequestBody AppTeam appTeam) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            JSONObject obj = new JSONObject();
            AppTeam queryTeam=appTeamService.setlectByTeamName(appTeam.getT_name());
            if(queryTeam!=null){
                result.success("重复球队名，请换一个");
                obj.put("data",null);
            }else{

                appTeamService.createTeam(appTeam);
                obj.put("data",appTeam);
                result.success("创建成功");
            }

            result.setResult(obj);
        }catch (Exception e){
            e.printStackTrace();
            result.error500("创建失败");
        }
        return result;
    }
    //判断是否队长
    @PostMapping(value = "/isCaptain")
    public Result<JSONObject> isCaptain() {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            JSONObject obj = new JSONObject();
            obj.put("isCaptain",appTeamService.isCaptain());
            result.setResult(obj);
            result.success("请求成功");
        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
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
    public Result<JSONObject> selectTeamByPage(@RequestBody  Map<String,Object> params) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            System.out.println(params);
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
    //查询球员
    @PostMapping(value = "/queryMyTeamPlayers")
    public Result<JSONObject> queryMyTeamPlayers(@RequestParam Integer id,@RequestParam String playerName) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            JSONObject obj = new JSONObject();
            List<AppTeamPlayersVo> players=appTeamPlayersService.selectByTeamId(id,playerName);
            obj.put("players",players);
            result.setResult(obj);
            result.success("请求成功");
        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }
        return result;
    }
    //转让队长
    @PostMapping(value = "/kickPlayer")
    public Result<JSONObject> changeLeader(@RequestParam Integer uId,@RequestParam Integer teamId) {
        Result<JSONObject> result = new Result<JSONObject>();
        try{
            appTeamPlayersService.kickPlayer(uId,teamId);
            result.success("请求成功");
        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }
        return result;
    }
    //转让队长
    @PostMapping(value = "/querTeamByMyApply")
    public Result<?> querTeamByMyApply(Integer uId) {
        try{
             return  Result.ok( appTeamMapper.querTeamByMyApply(uId));
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("请求失败");
        }
    }
}
