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
 * @Description: 队员管理
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Data
@TableName("playball_team_players")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_team_players对象", description="队员管理")
public class PlayballPlayer {
    
	/**tpId*/
	@TableId(type = IdType.AUTO)
	@Excel(name = "tpId", width = 15)
    @ApiModelProperty(value = "tpId")
	private java.lang.Integer tpId;
	/**关联用户表*/
	@Excel(name = "关联用户表", width = 15)
    @ApiModelProperty(value = "关联用户表")
	private java.lang.Integer uId;
	/**球员加入时间*/
	@Excel(name = "球员加入时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "球员加入时间")
	private java.util.Date tpJointime;
	/**退队时间*/
	@Excel(name = "退队时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "退队时间")
	private java.util.Date tpQuittime;
	/**所属球队*/
	@Excel(name = "所属球队", width = 15)
    @ApiModelProperty(value = "所属球队")
	private java.lang.Integer teamId;
	/**球衣号码*/
	@Excel(name = "球衣号码", width = 15)
    @ApiModelProperty(value = "球衣号码")
	private java.lang.Integer tpClothesNumber;
	/**关联球员位置表*/
	@Excel(name = "关联球员位置表", width = 15)
    @ApiModelProperty(value = "关联球员位置表")
	private java.lang.Integer tpPosition;
	/**纪录创建时间*/
	@Excel(name = "纪录创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "纪录创建时间")
	private java.util.Date createtime;
	/**纪录修改时间*/
	@Excel(name = "纪录修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "纪录修改时间")
	private java.util.Date updatetime;
	/**逻辑删除*/
	@Excel(name = "逻辑删除", width = 15)
    @ApiModelProperty(value = "逻辑删除")
	private java.lang.String isDelete;
	/**版本号（用作乐观锁）*/
	@Excel(name = "版本号（用作乐观锁）", width = 15)
    @ApiModelProperty(value = "版本号（用作乐观锁）")
	private java.lang.String version;
}
