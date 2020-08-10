package org.jeecg.modules.appapi.entity.vo;

import java.util.Date;

public class AboutMe {
    private Date queryNewOrdersTime;
    private Date queryMyAPpointmentGameTime;
    private Date queryMyJoinGameTime;
    private Integer uId;
    private Integer dynamicNum;
    private Integer mycollect;
    private Integer collectme;
    private Integer newOrders;
    private Integer myAPpointmentGame;
    private Integer myJoinGame;

    public Date getQueryNewOrdersTime() {
        return queryNewOrdersTime;
    }

    public void setQueryNewOrdersTime(Date queryNewOrdersTime) {
        this.queryNewOrdersTime = queryNewOrdersTime;
    }

    public Date getQueryMyAPpointmentGameTime() {
        return queryMyAPpointmentGameTime;
    }

    public void setQueryMyAPpointmentGameTime(Date queryMyAPpointmentGameTime) {
        this.queryMyAPpointmentGameTime = queryMyAPpointmentGameTime;
    }

    public Date getQueryMyJoinGameTime() {
        return queryMyJoinGameTime;
    }

    public void setQueryMyJoinGameTime(Date queryMyJoinGameTime) {
        this.queryMyJoinGameTime = queryMyJoinGameTime;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getDynamicNum() {
        return dynamicNum;
    }

    public void setDynamicNum(Integer dynamicNum) {
        this.dynamicNum = dynamicNum;
    }

    public Integer getMycollect() {
        return mycollect;
    }

    public void setMycollect(Integer mycollect) {
        this.mycollect = mycollect;
    }

    public Integer getCollectme() {
        return collectme;
    }

    public void setCollectme(Integer collectme) {
        this.collectme = collectme;
    }

    public Integer getNewOrders() {
        return newOrders;
    }

    public void setNewOrders(Integer newOrders) {
        this.newOrders = newOrders;
    }

    public Integer getMyAPpointmentGame() {
        return myAPpointmentGame;
    }

    public void setMyAPpointmentGame(Integer myAPpointmentGame) {
        this.myAPpointmentGame = myAPpointmentGame;
    }

    public Integer getMyJoinGame() {
        return myJoinGame;
    }

    public void setMyJoinGame(Integer myJoinGame) {
        this.myJoinGame = myJoinGame;
    }
}
