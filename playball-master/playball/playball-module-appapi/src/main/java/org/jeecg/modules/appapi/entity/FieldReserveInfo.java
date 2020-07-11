package org.jeecg.modules.appapi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * @Description: 场地预定
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Data
@TableName("playball_field_reserve_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_field_reserve_info对象", description="场地预定")
public class FieldReserveInfo {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**预定时间范围*/
	@Excel(name = "预定时间范围", width = 15)
    @ApiModelProperty(value = "预定时间范围")
	private String friTiemRanges;
	/**预定日期时间*/
	@Excel(name = "预定日期时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "预定日期时间")
	private Date friFieldday;
	/**所属场地*/
	@Excel(name = "所属场地", width = 15)
    @ApiModelProperty(value = "所属场地")
	private Integer fId;
	/**关联订单表ID*/
	@Excel(name = "关联订单表ID", width = 15)
    @ApiModelProperty(value = "关联订单表ID")
	private Integer ormId;
	/**场地类型*/
	@Excel(name = "场地类型", width = 15)
    @ApiModelProperty(value = "场地类型")
	private Integer stId;
	/**球队id*/
	@Excel(name = "球队id", width = 15)
    @ApiModelProperty(value = "球队id")
	private Integer teamId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
	private Integer userId;
	/**总价格*/
	@Excel(name = "总价格", width = 15)
    @ApiModelProperty(value = "总价格")
	private java.math.BigDecimal friPrice;
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
	private Integer isDelete;
	/**version*/
	@Excel(name = "version", width = 15)
    @ApiModelProperty(value = "version")
	private Integer version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFriTiemRanges() {
		return friTiemRanges;
	}

	public void setFriTiemRanges(String friTiemRanges) {
		this.friTiemRanges = friTiemRanges;
	}

	public Date getFriFieldday() {
		return friFieldday;
	}

	public void setFriFieldday(Date friFieldday) {
		this.friFieldday = friFieldday;
	}

	public Integer getfId() {
		return fId;
	}

	public void setfId(Integer fId) {
		this.fId = fId;
	}

	public Integer getOrmId() {
		return ormId;
	}

	public void setOrmId(Integer ormId) {
		this.ormId = ormId;
	}

	public Integer getStId() {
		return stId;
	}

	public void setStId(Integer stId) {
		this.stId = stId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public BigDecimal getFriPrice() {
		return friPrice;
	}

	public void setFriPrice(BigDecimal friPrice) {
		this.friPrice = friPrice;
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

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
