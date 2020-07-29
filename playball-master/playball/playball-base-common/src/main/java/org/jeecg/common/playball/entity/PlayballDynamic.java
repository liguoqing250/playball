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
 * @Description: 用户动态发布
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
@Data
@TableName("playball_user_dynamic")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_user_dynamic对象", description="用户动态发布")
public class PlayballDynamic {
    
	/**用户动态表主键id*/
	@TableId(type = IdType.AUTO)
	@Excel(name = "用户动态表主键id", width = 15)
    @ApiModelProperty(value = "用户动态表主键id")
	private java.lang.Integer udyId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
	private java.lang.Integer udyUserId;
	/**发布动态内容*/
	@Excel(name = "发布动态内容", width = 15)
    @ApiModelProperty(value = "发布动态内容")
	private java.lang.String udyContent;
	/**发布动态图片（最大支持 9 张图片）发布动态视频（最大支持 1 个视频）*/
	@Excel(name = "发布动态图片（最大支持 9 张图片）发布动态视频（最大支持 1 个视频）", width = 15)
    @ApiModelProperty(value = "发布动态图片（最大支持 9 张图片）发布动态视频（最大支持 1 个视频）")
	private java.lang.String udyResources;
	/**动态资源类型：1 图片 2视频*/
	@Excel(name = "动态资源类型：1 图片 2视频", width = 15)
    @ApiModelProperty(value = "动态资源类型：1 图片 2视频")
	private java.lang.Integer udyResType;
	/**发布动态信息位置（系统自动获取）*/
	@Excel(name = "发布动态信息位置（系统自动获取）", width = 15)
    @ApiModelProperty(value = "发布动态信息位置（系统自动获取）")
	private java.lang.String udyPosition;
	/**是否可见1、全部人2、仅自己*/
	@Excel(name = "是否可见1、全部人2、仅自己", width = 15)
    @ApiModelProperty(value = "是否可见1、全部人2、仅自己")
	private java.lang.Integer udyVisible;
	/**预编辑 1、已完成 2、草稿*/
	@Excel(name = "预编辑 1、已完成 2、草稿", width = 15)
    @ApiModelProperty(value = "预编辑 1、已完成 2、草稿")
	private java.lang.Integer udyDraft;
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
