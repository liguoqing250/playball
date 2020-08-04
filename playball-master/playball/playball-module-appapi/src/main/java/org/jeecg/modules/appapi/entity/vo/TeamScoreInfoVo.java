package org.jeecg.modules.appapi.entity.vo;

public class TeamScoreInfoVo {
    private Integer teamId;
    private Integer rownum;
    private float win;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getRownum() {
        return rownum;
    }

    public void setRownum(Integer rownum) {
        this.rownum = rownum;
    }

    public float getWin() {
        return win;
    }

    public void setWin(float win) {
        this.win = win;
    }
}
