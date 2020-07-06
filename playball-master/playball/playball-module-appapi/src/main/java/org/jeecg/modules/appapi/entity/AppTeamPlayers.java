package org.jeecg.modules.appapi.entity;

import java.util.Date;

/**
 * @className: AppTeamPlayers
 **/
public class AppTeamPlayers {

    //
    private Integer tp_id;
    //关联用户表
    private Integer u_id;
    //球员加入时间
    private Date tp_joinTime;
    //退队时间
    private Date tp_quitTime;
    //所属球队
    private Integer team_id;
    //球衣号码
    private Integer tp_clothes_number;
    //位置
    private String tp_position;
    //纪录创建时间
    private Date createTime;
    //纪录修改时间
    private Date updateTime;
    //逻辑删除
    private String is_delete;
    //版本号（用作乐观锁）
    private String version;


    public Integer getTp_id() {
        return tp_id;
    }

    public void setTp_id(Integer tp_id) {
        this.tp_id = tp_id;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public Date getTp_joinTime() {
        return tp_joinTime;
    }

    public void setTp_joinTime(Date tp_joinTime) {
        this.tp_joinTime = tp_joinTime;
    }

    public Date getTp_quitTime() {
        return tp_quitTime;
    }

    public void setTp_quitTime(Date tp_quitTime) {
        this.tp_quitTime = tp_quitTime;
    }

    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }

    public Integer getTp_clothes_number() {
        return tp_clothes_number;
    }

    public void setTp_clothes_number(Integer tp_clothes_number) {
        this.tp_clothes_number = tp_clothes_number;
    }

    public String getTp_position() {
        return tp_position;
    }

    public void setTp_position(String tp_position) {
        this.tp_position = tp_position;
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

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}