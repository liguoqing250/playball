package org.jeecg.modules.playball.entity;

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
 * @Description: 赛事信息
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Data
@TableName("playball_games")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_games对象", description="赛事信息")
public class PlayballGame implements Serializable  {
	private static final long serialVersionUID = 1L;
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**用整形来表示赛事的阶段，初始值0*/
	@Excel(name = "用整形来表示赛事的阶段，初始值0", width = 15)
    @ApiModelProperty(value = "用整形来表示赛事的阶段，初始值0")
	private java.lang.Integer stage;
	/*0表示赛事有比赛进行中，1表示所有比赛完毕*/
	private java.lang.Integer finished;
	/**运动类型*/
	@Excel(name = "运动类型", width = 15)
    @ApiModelProperty(value = "运动类型")
	private java.lang.Integer sportsId;
	/**场地id*/
	@Excel(name = "场地id", width = 15)
    @ApiModelProperty(value = "场地id")
	private java.lang.Integer fieldId;
	/**赛事名称*/
	@Excel(name = "赛事名称", width = 15)
    @ApiModelProperty(value = "赛事名称")
	private java.lang.String gamesName;
    @ApiModelProperty(value = "封面图片")
	private java.lang.String gameImage;
	/**赛事信息*/
	@Excel(name = "赛事信息", width = 15)
    @ApiModelProperty(value = "赛事信息")
	private java.lang.Object gamesInfo;
	/**gameRule*/
	@Excel(name = "gameRule", width = 15)
    @ApiModelProperty(value = "比赛规则")
	private java.lang.String gameRule;
	@Excel(name = "gameType", width = 15)
    @ApiModelProperty(value = "比赛类型(1小组赛+淘汰赛,2淘汰赛,3积分赛)")
	private java.lang.String gameType;
	/**报名时间*/
	@Excel(name = "报名时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报名时间")
	private java.util.Date enrollTime;
	/**赛事开始时间*/
	@Excel(name = "赛事开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "赛事开始时间")
	private java.util.Date startTime;
	/**赛事结束时间*/
	@Excel(name = "赛事结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "赛事结束时间")
	private java.util.Date endTime;
	/**createTime*/
	@Excel(name = "createTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
	private java.util.Date createTime;
	/**updateTime*/
	@Excel(name = "updateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
	private java.util.Date updateTime;
	/**isDelete*/
	@Excel(name = "isDelete", width = 15)
    @ApiModelProperty(value = "isDelete")
	private java.lang.Integer isDelete;
	/**version*/
	@Excel(name = "version", width = 15)
    @ApiModelProperty(value = "version")
	private java.lang.Integer version;
}
