package org.jeecg.common.playball.entity;

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

/**
 * @Description: 广告管理
 * @Author: jeecg-boot
 * @Date:   2020-08-01
 * @Version: V1.0
 */
@Data
@TableName("playball_poster")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_poster对象", description="广告管理")
public class PlayballAdvertisement {
    
	/**广告表主键id*/
	@TableId(type = IdType.AUTO)
	@Excel(name = "广告表主键id", width = 15)
    @ApiModelProperty(value = "广告表主键id")
	private java.lang.Integer posId;
	/**广告描述*/
	@Excel(name = "广告描述", width = 15)
    @ApiModelProperty(value = "广告描述")
	private java.lang.String posDescribe;
	/**广告轮播图*/
	@Excel(name = "广告轮播图", width = 15)
    @ApiModelProperty(value = "广告轮播图")
	private java.lang.String posRotation;
	/**广告类型:1、web页面 2、App本地页面*/
	@Excel(name = "广告类型:1、web页面 2、App本地页面", width = 15)
    @ApiModelProperty(value = "广告类型:1、web页面 2、App本地页面")
	private java.lang.Integer posType;
	/**跳转路径：web跳转用户输入、App 跳转自动填充*/
	@Excel(name = "跳转路径：web跳转用户输入、App 跳转自动填充", width = 15)
    @ApiModelProperty(value = "跳转路径：web跳转用户输入、App 跳转自动填充")
	private java.lang.String posJumpUrl;
	/**纪录创建时间*/
	@Excel(name = "纪录创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "纪录创建时间")
	private java.util.Date createTime;
	/**纪录修改时间*/
	@Excel(name = "纪录修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "纪录修改时间")
	private java.util.Date updateTime;
	/**逻辑删除*/
	@Excel(name = "逻辑删除", width = 15)
    @ApiModelProperty(value = "逻辑删除")
	private java.lang.Integer isDelete;
	/**版本号（用作乐观锁）*/
	@Excel(name = "版本号（用作乐观锁）", width = 15)
    @ApiModelProperty(value = "版本号（用作乐观锁）")
	private java.lang.Integer version;
}
