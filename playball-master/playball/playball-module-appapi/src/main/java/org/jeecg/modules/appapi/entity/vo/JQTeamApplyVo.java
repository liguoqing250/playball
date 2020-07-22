package org.jeecg.modules.appapi.entity.vo;

public class JQTeamApplyVo {
    private Integer uId;
    private Integer jqtaId;
    private Integer teamId;
    private String name;
    private String nickName;
    private String headImage;
    private Integer position;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getJqtaId() {
        return jqtaId;
    }

    public void setJqtaId(Integer jqtaId) {
        this.jqtaId = jqtaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }
}
