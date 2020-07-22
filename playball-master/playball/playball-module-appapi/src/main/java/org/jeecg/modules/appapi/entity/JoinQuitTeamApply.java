package org.jeecg.modules.appapi.entity;

import java.util.Date;

/**
 * @className: JoinQuitTeamApply
 * 
 **/
public class JoinQuitTeamApply {

    //
    private int jqta_id;
    //关联球队表
    private int team_id;
    //关联用户表
    private int u_id;
    //操作类型(1加入 2退出)
    private Integer jqta_type;
    //处理完成时间
    private Date jqta_handleTime;
    //处理结果(1通过 2不予通过)
    private int jqta_result;
    //纪录创建时间
    private Date createTime;
    //纪录修改时间
    private Date updateTime;
    //逻辑删除
    private int is_delete;
    //版本号（用作乐观锁）
    private int version;

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getJqta_id() {
        return jqta_id;
    }

    public void setJqta_id(int jqta_id) {
        this.jqta_id = jqta_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public Integer getJqta_type() {
        return jqta_type;
    }

    public void setJqta_type(Integer jqta_type) {
        this.jqta_type = jqta_type;
    }

    public Date getJqta_handleTime() {
        return jqta_handleTime;
    }

    public void setJqta_handleTime(Date jqta_handleTime) {
        this.jqta_handleTime = jqta_handleTime;
    }

    public int getJqta_result() {
        return jqta_result;
    }

    public void setJqta_result(int jqta_result) {
        this.jqta_result = jqta_result;
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

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}