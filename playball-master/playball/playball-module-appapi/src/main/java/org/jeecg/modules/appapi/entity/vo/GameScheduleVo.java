package org.jeecg.modules.appapi.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description: 比赛
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Data
@TableName("playball_game_schedule")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_game_schedule对象", description="比赛")
public class GameScheduleVo {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**比赛阶段，用来表示该比赛处于赛事的阶段，便于后面对比赛管控*/
	@Excel(name = "比赛阶段，用来表示该比赛处于赛事的阶段，便于后面对比赛管控", width = 15)
    @ApiModelProperty(value = "比赛阶段，用来表示该比赛处于赛事的阶段，便于后面对比赛管控")
	private Integer stage;
	/**赛事id*/
	@Excel(name = "赛事id", width = 15)
    @ApiModelProperty(value = "赛事id")
	private Integer gamesId;
	/**球队id*/
	@Excel(name = "球队id", width = 15)
    @ApiModelProperty(value = "球队id")
	private AppTeamVo teama;
	/**比赛日期*/
	@Excel(name = "比赛日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "比赛日期")
	private Date matchTime;
	/**分组id，淘汰赛不用分组*/
	@Excel(name = "分组id，淘汰赛不用分组", width = 15)
    @ApiModelProperty(value = "分组id，淘汰赛不用分组")
	private String groupId;
	/**对手id（对方球队id）*/
	@Excel(name = "对手id（对方球队id）", width = 15)
    @ApiModelProperty(value = "对手id（对方球队id）")
	private AppTeamVo teamb;
	/** 比赛状态1已赛2未赛*/
	@Excel(name = " 比赛状态1已赛2未赛", width = 15)
    @ApiModelProperty(value = " 比赛状态1已赛2未赛")
	private Integer gameStatus;
	/**比赛结果1胜2平3负*/
	@Excel(name = "比赛结果1胜2平3负", width = 15)
    @ApiModelProperty(value = "比赛结果1胜2平3负")
	private Integer gameResult;
	/**进球数*/
	@Excel(name = "进球数", width = 15)
    @ApiModelProperty(value = "进球数")
	private Integer enterBall;
	/**失球数*/
	@Excel(name = "失球数", width = 15)
    @ApiModelProperty(value = "失球数")
	private Integer lostBall;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public Integer getGamesId() {
		return gamesId;
	}

	public void setGamesId(Integer gamesId) {
		this.gamesId = gamesId;
	}

	public AppTeamVo getTeama() {
		return teama;
	}

	public void setTeama(AppTeamVo teama) {
		this.teama = teama;
	}

	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public AppTeamVo getTeamb() {
		return teamb;
	}

	public void setTeamb(AppTeamVo teamb) {
		this.teamb = teamb;
	}

	public Integer getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(Integer gameStatus) {
		this.gameStatus = gameStatus;
	}

	public Integer getGameResult() {
		return gameResult;
	}

	public void setGameResult(Integer gameResult) {
		this.gameResult = gameResult;
	}

	public Integer getEnterBall() {
		return enterBall;
	}

	public void setEnterBall(Integer enterBall) {
		this.enterBall = enterBall;
	}

	public Integer getLostBall() {
		return lostBall;
	}

	public void setLostBall(Integer lostBall) {
		this.lostBall = lostBall;
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
