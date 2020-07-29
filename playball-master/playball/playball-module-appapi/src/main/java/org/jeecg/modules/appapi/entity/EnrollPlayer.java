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

/**
 * @Description: 参赛球员
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
@Data
@TableName("playball_enroll_player")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_enroll_player对象", description="参赛球员")
public class EnrollPlayer {
    
	/**主键id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
	private Integer id;
	/**报名id（关联报名表）*/
	@Excel(name = "报名id（关联报名表）", width = 15)
    @ApiModelProperty(value = "报名id（关联报名表）")
	private Integer enrollId;
	/**用户id*/
	@Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
	private String userId;
	/**球员id*/
	@Excel(name = "球员id", width = 15)
    @ApiModelProperty(value = "球员id")
	private String playerId;
	/**createTime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
	private Date createTime;
	/**updateTime*/
	@Excel(name = "updateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
	private Date updateTime;
	/**isDelete*/
	@Excel(name = "isDelete", width = 15)
    @ApiModelProperty(value = "isDelete")
	private Integer isDelete;
	/**version*/
	@Excel(name = "version", width = 15)
    @ApiModelProperty(value = "version")
	private Integer version;
}
