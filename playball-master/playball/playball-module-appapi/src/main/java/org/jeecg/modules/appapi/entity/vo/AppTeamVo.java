package org.jeecg.modules.appapi.entity.vo;

import java.util.Date;

/**
 * @className: AppTeam
 **/
public class AppTeamVo {

    private Integer team_id;
    private String t_name;
    private String t_image;
    private String u_nickName;
    private String u_name;

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_image() {
        return t_image;
    }

    public void setT_image(String t_image) {
        this.t_image = t_image;
    }

    public String getU_nickName() {
        return u_nickName;
    }

    public void setU_nickName(String u_nickName) {
        this.u_nickName = u_nickName;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }
}