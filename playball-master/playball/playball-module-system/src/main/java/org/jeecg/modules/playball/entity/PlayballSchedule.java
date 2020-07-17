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
 * @Description: 赛程表
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
@Data
@TableName("playball_game_schedule")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_game_schedule对象", description="赛程表")
public class PlayballSchedule {
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**比赛阶段，用来表示该比赛处于赛事的阶段，便于后面对比赛管控*/
	@Excel(name = "比赛阶段，用来表示该比赛处于赛事的阶段，便于后面对比赛管控", width = 15)
    @ApiModelProperty(value = "比赛阶段，用来表示该比赛处于赛事的阶段，便于后面对比赛管控")
	private java.lang.Integer stage;
	/**赛事id*/
	@Excel(name = "赛事id", width = 15)
    @ApiModelProperty(value = "赛事id")
	private java.lang.Integer gamesId;
	/**球队id*/
	@Excel(name = "球队id", width = 15)
    @ApiModelProperty(value = "球队id")
	private java.lang.Integer teamId;
	/**比赛日期*/
	@Excel(name = "比赛日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "比赛日期")
	private java.util.Date matchTime;
	/**分组id，淘汰赛不用分组*/
	@Excel(name = "分组id，淘汰赛不用分组", width = 15)
    @ApiModelProperty(value = "分组id，淘汰赛不用分组")
	private java.lang.String groupId;
	/**对手id（对方球队id）*/
	@Excel(name = "对手id（对方球队id）", width = 15)
    @ApiModelProperty(value = "对手id（对方球队id）")
	private java.lang.Integer opponentId;
	/** 比赛状态1已赛2未赛*/
	@Excel(name = " 比赛状态1已赛2未赛", width = 15)
    @ApiModelProperty(value = " 比赛状态1已赛2未赛")
	private java.lang.Integer gameStatus;
	/**比赛结果1胜2平3负*/
	@Excel(name = "比赛结果1胜2平3负", width = 15)
    @ApiModelProperty(value = "比赛结果1胜2平3负")
	private java.lang.Integer gameResult;
	/**进球数*/
	@Excel(name = "进球数", width = 15)
    @ApiModelProperty(value = "进球数")
	private java.lang.Integer enterBall;
	/**失球数*/
	@Excel(name = "失球数", width = 15)
    @ApiModelProperty(value = "失球数")
	private java.lang.Integer lostBall;
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
