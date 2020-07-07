package org.jeecg.modules.appapi.entity;

import java.util.Date;

/**
 * @className: AppTeam
 **/
public class AppTeam {

    //
    private Integer team_id;
    //队名
    private String t_name;
    //队徽（图片一张）
    private String t_image;
    //所属单位名
    private String t_unitname;
    //球队类型(关联运动类型表)
    private Integer st_id;
    //当前球队人数
    private Integer t_players_total;
    //球队人数上限
    private Integer t_players_max;
    //球队介绍 招募说明
    private String t_introduce;
    //胜 次数
    private Integer t_win_total;
    //平 次数
    private Integer t_divide_total;
    //负 次数
    private Integer t_lost_total;
    //球队创建人
    private Integer u_id;
    //所属省--对应地区表主键
    private Integer t_province;
    //所属市--对应地区表主键
    private Integer t_city;
    //所属区--对应地区表主键
    private Integer t_district;
    //建队时间
    private Date t_createTeamTime;
    //队长 球员表ID  p_id
    private Integer t_captain;
    //纪录创建时间
    private Date createTime;
    //纪录修改时间
    private Date updateTime;
    //逻辑删除
    private Integer is_delete;
    //版本号（用作乐观锁）
    private Integer version;

    public Integer getT_players_max() {
        return t_players_max;
    }

    public void setT_players_max(Integer t_players_max) {
        this.t_players_max = t_players_max;
    }

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

    public String getT_unitname() {
        return t_unitname;
    }

    public void setT_unitname(String t_unitname) {
        this.t_unitname = t_unitname;
    }

    public Integer getSt_id() {
        return st_id;
    }

    public void setSt_id(Integer st_id) {
        this.st_id = st_id;
    }

    public Integer getT_players_total() {
        return t_players_total;
    }

    public void setT_players_total(Integer t_players_total) {
        this.t_players_total = t_players_total;
    }

    public String getT_introduce() {
        return t_introduce;
    }

    public void setT_introduce(String t_introduce) {
        this.t_introduce = t_introduce;
    }

    public Integer getT_win_total() {
        return t_win_total;
    }

    public void setT_win_total(Integer t_win_total) {
        this.t_win_total = t_win_total;
    }

    public Integer getT_divide_total() {
        return t_divide_total;
    }

    public void setT_divide_total(Integer t_divide_total) {
        this.t_divide_total = t_divide_total;
    }

    public Integer getT_lost_total() {
        return t_lost_total;
    }

    public void setT_lost_total(Integer t_lost_total) {
        this.t_lost_total = t_lost_total;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public Integer getT_province() {
        return t_province;
    }

    public void setT_province(Integer t_province) {
        this.t_province = t_province;
    }

    public Integer getT_city() {
        return t_city;
    }

    public void setT_city(Integer t_city) {
        this.t_city = t_city;
    }

    public Integer getT_district() {
        return t_district;
    }

    public void setT_district(Integer t_district) {
        this.t_district = t_district;
    }

    public Date getT_createTeamTime() {
        return t_createTeamTime;
    }

    public void setT_createTeamTime(Date t_createTeamTime) {
        this.t_createTeamTime = t_createTeamTime;
    }

    public Integer getT_captain() {
        return t_captain;
    }

    public void setT_captain(Integer t_captain) {
        this.t_captain = t_captain;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(Integer is_delete) {
        this.is_delete = is_delete;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}