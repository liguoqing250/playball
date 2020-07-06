package org.jeecg.modules.appapi.entity;

import java.util.Date;

/**
 * @className: AppUsers
 **/
public class AppUsers {

    //
    private Integer u_id;
    //用户名
    private String u_name;
    //昵称
    private String u_nickName;
    //手机号--唯一约束
    private String u_phoneNumber;
    //密码，非空约束
    private String u_passworld;
    //性别 1男/ 2女
    private String u_sex;
    //年龄
    private String u_age;
    //身高 单位 cm
    private Integer u_height;
    //体重 单位 kg
    private Integer u_weight;
    //头像
    private String u_headImage;
    //1 手机 2QQ   3微信
    private Integer u_registerWay;
    //QQ--唯一约束
    private String wx_openid;
    //微信--唯一约束
    private String qq_openid;
    //支付宝账号
    private String u_alipayid;
    //身份证号
    private String u_idCard;
    //所属省--对应地区表主键
    private Integer u_province;
    //所属市--对应地区表主键
    private Integer u_city;
    //所属区--对应地区表主键
    private Integer u_district;
    //详细地址
    private String u_address;
    //最后登录时间
    private Date u_lastlogin_time;
    //邀请人()
    private Integer u_invited_user;
    //数据创建时间
    private Date createTime;
    //数据更新时间
    private Date updateTime;
    //是否已删除1删除  0未删除 默认0 
    private Integer is_delete;
    //版本号（用作乐观锁）
    private String version;

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getU_nickName() {
        return u_nickName;
    }

    public void setU_nickName(String u_nickName) {
        this.u_nickName = u_nickName;
    }

    public String getU_phoneNumber() {
        return u_phoneNumber;
    }

    public void setU_phoneNumber(String u_phoneNumber) {
        this.u_phoneNumber = u_phoneNumber;
    }

    public String getU_passworld() {
        return u_passworld;
    }

    public void setU_passworld(String u_passworld) {
        this.u_passworld = u_passworld;
    }

    public String getU_sex() {
        return u_sex;
    }

    public void setU_sex(String u_sex) {
        this.u_sex = u_sex;
    }

    public String getU_age() {
        return u_age;
    }

    public void setU_age(String u_age) {
        this.u_age = u_age;
    }

    public Integer getU_height() {
        return u_height;
    }

    public void setU_height(Integer u_height) {
        this.u_height = u_height;
    }

    public Integer getU_weight() {
        return u_weight;
    }

    public void setU_weight(Integer u_weight) {
        this.u_weight = u_weight;
    }

    public String getU_headImage() {
        return u_headImage;
    }

    public void setU_headImage(String u_headImage) {
        this.u_headImage = u_headImage;
    }

    public Integer getU_registerWay() {
        return u_registerWay;
    }

    public void setU_registerWay(Integer u_registerWay) {
        this.u_registerWay = u_registerWay;
    }

    public String getWx_openid() {
        return wx_openid;
    }

    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid;
    }

    public String getQq_openid() {
        return qq_openid;
    }

    public void setQq_openid(String qq_openid) {
        this.qq_openid = qq_openid;
    }

    public String getU_alipayid() {
        return u_alipayid;
    }

    public void setU_alipayid(String u_alipayid) {
        this.u_alipayid = u_alipayid;
    }

    public String getU_idCard() {
        return u_idCard;
    }

    public void setU_idCard(String u_idCard) {
        this.u_idCard = u_idCard;
    }

    public Integer getU_province() {
        return u_province;
    }

    public void setU_province(Integer u_province) {
        this.u_province = u_province;
    }

    public Integer getU_city() {
        return u_city;
    }

    public void setU_city(Integer u_city) {
        this.u_city = u_city;
    }

    public Integer getU_district() {
        return u_district;
    }

    public void setU_district(Integer u_district) {
        this.u_district = u_district;
    }

    public String getU_address() {
        return u_address;
    }

    public void setU_address(String u_address) {
        this.u_address = u_address;
    }

    public Date getU_lastlogin_time() {
        return u_lastlogin_time;
    }

    public void setU_lastlogin_time(Date u_lastlogin_time) {
        this.u_lastlogin_time = u_lastlogin_time;
    }

    public Integer getU_invited_user() {
        return u_invited_user;
    }

    public void setU_invited_user(Integer u_invited_user) {
        this.u_invited_user = u_invited_user;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}