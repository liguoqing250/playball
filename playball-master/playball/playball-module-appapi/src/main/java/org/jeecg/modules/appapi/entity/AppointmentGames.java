package org.jeecg.modules.appapi.entity;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * @Description: 约球
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Data
@TableName("playball_appointment_games")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_appointment_games对象", description="约球")
public class AppointmentGames {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**运动类型id*/
	@Excel(name = "运动类型id", width = 15)
    @ApiModelProperty(value = "运动类型id")
	private Integer stId;
	/**最大参与人数*/
	@Excel(name = "最大参与人数", width = 15)
    @ApiModelProperty(value = "最大参与人数")
	private Integer agMaxplayers;
	/**约球信息*/
	@Excel(name = "约球信息", width = 15)
    @ApiModelProperty(value = "约球信息")
	private String agInfo;
	/**发布者id*/
	@Excel(name = "发布者id", width = 15)
    @ApiModelProperty(value = "发布者id")
	private Integer agUserId;
	/**约球时间*/
	@Excel(name = "约球时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "约球时间")
	private Date agAppointmentTime;
	/**失效时间*/
	@Excel(name = "失效时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "失效时间")
	private Date agClosingTime;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
	private Integer agStatus;
	/**地区id*/
	@Excel(name = "场地id", width = 15)
    @ApiModelProperty(value = "场地id")
	private Integer fId;
	/**地区id*/
	@Excel(name = "详细位置", width = 15)
    @ApiModelProperty(value = "地区id")
	private String agAddress;
	/**商家id*/
	@Excel(name = "商家id", width = 15)
    @ApiModelProperty(value = "商家id")
	private String bId;
	/**付费规则 1AA 2输赢制*/
	@Excel(name = "付费规则 1AA 2输赢制", width = 15)
    @ApiModelProperty(value = "付费规则 1AA 2输赢制")
	private Integer agPayRule;
	/**约球主体id*/
	@Excel(name = "约球主体id", width = 15)
    @ApiModelProperty(value = "约球主体id")
	private Integer agSubjectId;
	/**发布者所属球队id*/
	@Excel(name = "发布者所属球队id", width = 15)
    @ApiModelProperty(value = "发布者所属球队id")
	private Integer tpId;
	/**约球说明*/
	@Excel(name = "约球说明", width = 15)
    @ApiModelProperty(value = "约球说明")
	private String agExplain;
	/**createtime*/
	@Excel(name = "createtime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createtime")
	private Date createtime;
	/**updatetime*/
	@Excel(name = "updatetime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updatetime")
	private Date updatetime;
	/**isDelete*/
	@Excel(name = "isDelete", width = 15)
    @ApiModelProperty(value = "isDelete")
	private String isDelete;
	/**version*/
	@Excel(name = "version", width = 15)
    @ApiModelProperty(value = "version")
	private String version;

	@TableField(exist = false)
	private AppUsers userInfo;

	//约球方式
	@TableField(exist = false)
	private Integer appointmentWay;

	public Integer getAppointmentWay() {
		return appointmentWay;
	}

	public void setAppointmentWay(Integer appointmentWay) {
		this.appointmentWay = appointmentWay;
	}

	public AppUsers getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(AppUsers userInfo) {
		this.userInfo = userInfo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStId() {
		return stId;
	}

	public void setStId(Integer stId) {
		this.stId = stId;
	}

	public Integer getAgMaxplayers() {
		return agMaxplayers;
	}

	public void setAgMaxplayers(Integer agMaxplayers) {
		this.agMaxplayers = agMaxplayers;
	}

	public String getAgInfo() {
		return agInfo;
	}

	public void setAgInfo(String agInfo) {
		this.agInfo = agInfo;
	}

	public Integer getAgUserId() {
		return agUserId;
	}

	public void setAgUserId(Integer agUserId) {
		this.agUserId = agUserId;
	}

	public Date getAgAppointmentTime() {
		return agAppointmentTime;
	}

	public void setAgAppointmentTime(Date agAppointmentTime) {
		this.agAppointmentTime = agAppointmentTime;
	}

	public Date getAgClosingTime() {
		return agClosingTime;
	}

	public void setAgClosingTime(Date agClosingTime) {
		this.agClosingTime = agClosingTime;
	}

	public Integer getAgStatus() {
		return agStatus;
	}

	public void setAgStatus(Integer agStatus) {
		this.agStatus = agStatus;
	}

	public Integer getfId() {
		return fId;
	}

	public void setfId(Integer fId) {
		this.fId = fId;
	}

	public String getAgAddress() {
		return agAddress;
	}

	public void setAgAddress(String agAddress) {
		this.agAddress = agAddress;
	}

	public String getbId() {
		return bId;
	}

	public void setbId(String bId) {
		this.bId = bId;
	}

	public Integer getAgPayRule() {
		return agPayRule;
	}

	public void setAgPayRule(Integer agPayRule) {
		this.agPayRule = agPayRule;
	}

	public Integer getAgSubjectId() {
		return agSubjectId;
	}

	public void setAgSubjectId(Integer agSubjectId) {
		this.agSubjectId = agSubjectId;
	}

	public Integer getTpId() {
		return tpId;
	}

	public void setTpId(Integer tpId) {
		this.tpId = tpId;
	}

	public String getAgExplain() {
		return agExplain;
	}

	public void setAgExplain(String agExplain) {
		this.agExplain = agExplain;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


}
