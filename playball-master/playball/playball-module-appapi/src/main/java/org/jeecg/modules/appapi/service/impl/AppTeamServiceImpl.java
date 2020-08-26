package org.jeecg.modules.appapi.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.map.HashedMap;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.entity.AppTeamPlayers;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.UserNotice;
import org.jeecg.modules.appapi.entity.vo.TeamScoreInfoVo;
import org.jeecg.modules.appapi.mapper.*;
import org.jeecg.modules.appapi.service.AppTeamService;
import org.jeecg.modules.appapi.service.IUserNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class AppTeamServiceImpl  implements AppTeamService {
    @Autowired
    private IUserNoticeService userNoticeService;
    @Autowired
    TeamRecruitsMapper teamRecruitsMapper;
    @Autowired
    AppTeamMapper appTeamMapper;

    @Autowired
    AppTeamPlayersMapper appTeamPlayersMapper;
    @Autowired
    AppointmentGamesMapper appointmentGamesMapper;
    @Autowired
    EnrollMapper enrollMapper;
    @Autowired
    EnrollPlayerMapper enrollPlayerMapper;
    @Autowired
    JoinQuitTeamApplyMapper joinQuitTeamApplyMapper;
    //解散球队
    @Override
    public void disbandTeam() {
        //获取当前用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token=request.getHeader("X-Access-Token");
        AppUsers appUsers=JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);

        AppTeam appTeam=appTeamMapper.isCaptain(appUsers.getU_id());

        Map<String,Object> map =new HashedMap();
        map.put("team_id",appTeam.getTeam_id());
        List<AppTeamPlayers> list= appTeamPlayersMapper.selectByKey(map);
        for (int i = 0; i < list.size(); i++) {
            UserNotice userNotice=new UserNotice();
            userNotice.setType(3);
            userNotice.setReceiverUid(list.get(i).getU_id());
            userNotice.setContent("已退出 "+appTeam.getT_name());
            userNoticeService.save(userNotice);
        }
        //删除球队表
        appTeam.setIs_delete(1);
        appTeamMapper.update(appTeam);
        //删除队员表
        appTeamPlayersMapper.removePlayer(appTeam.getTeam_id());
        //删除招募信息
        teamRecruitsMapper.deleteByTeamId(appTeam.getTeam_id());
        //删除招募申请
        joinQuitTeamApplyMapper.deleteByTeamId(appTeam.getTeam_id());
        //删除比赛报报名
        enrollMapper.deleteByTeamId(appTeam.getTeam_id());
        //删除报名球员
        enrollPlayerMapper.deleteByTeamId(appTeam.getTeam_id());
        //删除约球
        appointmentGamesMapper.deleteByTeamId(appTeam.getTeam_id());
    }
    @Override
    public void createTeam(AppTeam record) {
        //获取当前用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token=request.getHeader("X-Access-Token");
        AppUsers appUsers=JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
        //删除删除申请
        joinQuitTeamApplyMapper.deleteByUId(appUsers.getU_id());
        //创建球队
        record.setU_id(appUsers.getU_id());
        record.setT_players_total(1);
        record.setT_captain(appUsers.getU_id());
        appTeamMapper.insert(record);
        //创建队员信息
        AppTeamPlayers appTeamPlayers=new AppTeamPlayers();

        appTeamPlayers.setTeam_id(record.getTeam_id());
        appTeamPlayers.setTp_joinTime(new Date());
        appTeamPlayers.setU_id(appUsers.getU_id());
        appTeamPlayersMapper.insert(appTeamPlayers);
    }

    @Override
    public void insert(AppTeam record) {
        appTeamMapper.insert(record);
    }

    @Override
    public void deleteById(int id) {
        appTeamMapper.deleteById(id);
    }

    @Override
    public void update(AppTeam record) {
        appTeamMapper.update(record);
    }


    @Override

    public AppTeam selectById(int id) {
        return appTeamMapper.selectById(id);
    }

    @Override
    public boolean isJoinTeam() {
        //获取当前用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token=request.getHeader("X-Access-Token");
        AppUsers appUsers=JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
       AppTeamPlayers appTeamPlayers= appTeamPlayersMapper.selectByUserId(appUsers.getU_id());
       if(appTeamPlayers!=null){
           return true;
       }
        return false;
    }

    @Override
    public AppTeam selectMyTeamInfo() {
        //获取当前用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token=request.getHeader("X-Access-Token");
        AppUsers appUsers=JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
        AppTeamPlayers appTeamPlayers= appTeamPlayersMapper.selectByUserId(appUsers.getU_id());
        AppTeam appTeam= appTeamMapper.selectById(appTeamPlayers.getTeam_id());
        return appTeam;
    }

    @Override
    public Page<AppTeam> selectByPage( Map<String, Object> params) {
        Page<AppTeam> page=new Page<>();
        //int current=(int)params.get("current");
        int PageSize=(int)params.get("PageSize");
        //int PageNum=(current-1)*PageSize+1;
       // params.put("PageNum",PageNum);
        page.setRecords(appTeamMapper.selectByPage(params));
        page.setSize(PageSize);
       // page.setCurrent(current);

        int count=appTeamMapper.getCount(params);
        Long pageSize=page.getSize();
        int totalPages = count / pageSize.intValue(); //JAVA整数之间的除法运算，默认只返回整数位
        if(count % page.getSize() != 0) totalPages++;
        page.setPages(totalPages);
        page.setTotal(totalPages);

        return page;
    }

    @Override
    public boolean isCaptain() {
        //获取当前用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token=request.getHeader("X-Access-Token");
        AppUsers appUsers=JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
        AppTeam appTeam=appTeamMapper.isCaptain(appUsers.getU_id());
        if(appTeam!=null){
            return true;
        }
        return false;
    }

    @Override
    public List<AppTeam> queryTeamByGameid(Integer id) {
        return appTeamMapper.queryTeamByGameid(id);
    }

    @Override
    public List<AppTeam> queryTeamByEnroll(Integer id) {
        return appTeamMapper.queryTeamByEnroll(id);
    }



    @Override
    public AppTeam setlectByTeamName(String t_name) {
        Map<String,Object> map=new HashedMap();
        map.put("t_name",t_name);
        List<AppTeam> list=appTeamMapper.selectByKey(map);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public TeamScoreInfoVo selectTeamScoreInfoById(Integer id) {
        return appTeamMapper.selectTeamScoreInfoById(id);
    }
}
