package org.jeecg.modules.appapi.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections.map.HashedMap;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.appapi.entity.*;
import org.jeecg.modules.appapi.entity.vo.JQTeamApplyVo;
import org.jeecg.modules.appapi.mapper.AppTeamMapper;
import org.jeecg.modules.appapi.mapper.JoinQuitTeamApplyMapper;
import org.jeecg.modules.appapi.service.AppTeamPlayersService;
import org.jeecg.modules.appapi.service.IUserNoticeService;
import org.jeecg.modules.appapi.service.JoinQuitTeamApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JoinQuitTeamApplyServiceImpl implements JoinQuitTeamApplyService {
    @Autowired
    AppTeamMapper appTeamMapper;
    @Autowired
    AppTeamPlayersService appTeamPlayersService;
    @Autowired
    private IUserNoticeService userNoticeService;
    @Autowired
    JoinQuitTeamApplyMapper mapper;

    @Autowired
    private HttpSession session;

    @Override
    public JSONObject joinTeamApply(JoinQuitTeamApply record) {
        JSONObject obj = new JSONObject();
        //获取当前用户信息
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token=request.getHeader("X-Access-Token");
        AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
        AppTeamPlayers appTeamPlayers= appTeamPlayersService.selectByuId(appUsers.getU_id());
        if(appTeamPlayers!=null){
            obj.put("msg","请先退出当前球队再尝试申请");
            return  obj;
        }
        Map<String,Object> parmers=new HashMap<>();
        parmers.put("team_id",record.getTeam_id());
        parmers.put("u_id",appUsers.getU_id());
        List<JoinQuitTeamApply> oldApply=mapper.selectIsApply(parmers);
        if(oldApply.size()==0){
            record.setU_id(appUsers.getU_id());
            mapper.insert(record);
            obj.put("msg","申请成功");
            AppTeam appTeam=appTeamMapper.selectById(record.getTeam_id());
            UserNotice userNotice=new UserNotice();
            userNotice.setType(2);
            userNotice.setReceiverUid(appUsers.getU_id());
            userNotice.setContent("已申请加入 "+appTeam.getT_name());
            userNoticeService.save(userNotice);
        }else{
            obj.put("msg","已申请,请勿重复申请");
        }
        return obj;
    }
    @Override
    public void deleteById(Integer id) {
         mapper.deleteById(id);
    }

    @Override
    public JoinQuitTeamApply selectById(Integer id) {
        return mapper.selectById(id);
    }

    @Override
    public void update(JoinQuitTeamApply record) {
         mapper.update(record);
    }
    @Override
    public List<JoinQuitTeamApply> selectByKey(Map<String,Object> map){
         return mapper.selectByKey(map);
    };

    @Override
    public Page selectByPage(Map<String,Object> map) {
        Integer totalNum=mapper.getCount(map);
        Page page=new Page();

        return page;
    }

    @Override
    public List<JQTeamApplyVo> queryJoinQuitTeamApply(Integer jqtaType, String playerName) {
        Map<String,Object> map=new HashedMap();
        map.put("jqtaType",jqtaType);
        map.put("playerName",playerName);
        return mapper.queryJoinQuitTeamApply(map);
    }


}