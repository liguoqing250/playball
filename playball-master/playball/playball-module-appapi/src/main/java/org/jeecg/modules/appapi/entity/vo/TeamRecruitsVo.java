package org.jeecg.modules.appapi.entity.vo;

public class TeamRecruitsVo {
    private  Integer id;
    private  Integer teamId;
    private  String  maxPlayers;
    private  String  explainInfo;
    private  String  text;
    private  String  name;
    private  Integer playersTotal;
    private  String  image;
    private  String  province;
    private  String  city;
    private  String  district;
    private  String  sportsName;
    private  Integer position;
    private  Integer winTotal;
    private  Integer divideTotal;
    private  Integer lostTotal;

    public Integer getWinTotal() {
        return winTotal;
    }

    public void setWinTotal(Integer winTotal) {
        this.winTotal = winTotal;
    }

    public Integer getDivideTotal() {
        return divideTotal;
    }

    public void setDivideTotal(Integer divideTotal) {
        this.divideTotal = divideTotal;
    }

    public Integer getLostTotal() {
        return lostTotal;
    }

    public void setLostTotal(Integer lostTotal) {
        this.lostTotal = lostTotal;
    }

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

    public String getSportsName() {
        return sportsName;
    }

    public void setSportsName(String sportsName) {
        this.sportsName = sportsName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(String maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getExplainInfo() {
        return explainInfo;
    }

    public void setExplainInfo(String explainInfo) {
        this.explainInfo = explainInfo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlayersTotal() {
        return playersTotal;
    }

    public void setPlayersTotal(Integer playersTotal) {
        this.playersTotal = playersTotal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
