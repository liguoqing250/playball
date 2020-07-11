package org.jeecg.modules.appapi.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.appapi.entity.AcceptAppointment;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.entity.AppUsers;
import org.jeecg.modules.appapi.entity.AppointmentGames;
import org.jeecg.modules.appapi.mapper.AcceptAppointmentMapper;
import org.jeecg.modules.appapi.service.AppTeamService;
import org.jeecg.modules.appapi.service.IAcceptAppointmentService;
import org.jeecg.modules.appapi.service.IAppointmentGamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 应约
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Service
public class AcceptAppointmentServiceImpl extends ServiceImpl<AcceptAppointmentMapper, AcceptAppointment> implements IAcceptAppointmentService {
    @Autowired
    HttpServletRequest request;
    @Autowired
    AppTeamService appTeamService;
    @Autowired
    AcceptAppointmentMapper acceptAppointmentMapper;
    @Autowired
    private IAppointmentGamesService appointmentGamesService;
    @Override
    public boolean isJoinAppointment(Integer id) {
        String token=request.getHeader("X-Access-Token");
        AppUsers appUsers= JSONObject.parseObject( JwtUtil.getUserInfo(token),AppUsers.class);
        AppointmentGames appointmentGames= appointmentGamesService.getById(id);
        Map<String,Object> map=new HashMap<>();
        map.put("ag_id",id);
        if(appointmentGames.getTpId()!=null){
            //球队
            map.put("aa_accept_subject_id",appTeamService.selectMyTeamInfo().getTeam_id());
        }else{
            //个人
            map.put("aa_accept_subject_id",appUsers.getU_id());
        }

        List<AcceptAppointment> list=acceptAppointmentMapper.selectByMap(map);
        if(list.size()>0){
            return true;
        }
        return false;
    }
}
