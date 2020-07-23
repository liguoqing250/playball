package org.jeecg.modules.appapi.entity.vo;

import java.util.Date;

public class AppTeamPlayersVo {
    private Integer tpId;
    //关联用户表
    private Integer uId;
    //球员加入时间
    private Date tpJoinTime;
    //退队时间
    private Date tpQuitTime;
    //所属球队
    private Integer teamId;
    //球衣号码
    private Integer tpClothesNumber;
    //位置
    private Integer tpPosition;
    private String uName;
    private String uNickName;
    private String uHeadImage;
    private String positionNmame;

    public String getPositionNmame() {
        return positionNmame;
    }

    public void setPositionNmame(String positionNmame) {
        this.positionNmame = positionNmame;
    }

    public Integer getTpId() {
        return tpId;
    }

    public void setTpId(Integer tpId) {
        this.tpId = tpId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Date getTpJoinTime() {
        return tpJoinTime;
    }

    public void setTpJoinTime(Date tpJoinTime) {
        this.tpJoinTime = tpJoinTime;
    }

    public Date getTpQuitTime() {
        return tpQuitTime;
    }

    public void setTpQuitTime(Date tpQuitTime) {
        this.tpQuitTime = tpQuitTime;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTpClothesNumber() {
        return tpClothesNumber;
    }

    public void setTpClothesNumber(Integer tpClothesNumber) {
        this.tpClothesNumber = tpClothesNumber;
    }

    public Integer getTpPosition() {
        return tpPosition;
    }

    public void setTpPosition(Integer tpPosition) {
        this.tpPosition = tpPosition;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuNickName() {
        return uNickName;
    }

    public void setuNickName(String uNickName) {
        this.uNickName = uNickName;
    }

    public String getuHeadImage() {
        return uHeadImage;
    }

    public void setuHeadImage(String uHeadImage) {
        this.uHeadImage = uHeadImage;
    }
}
