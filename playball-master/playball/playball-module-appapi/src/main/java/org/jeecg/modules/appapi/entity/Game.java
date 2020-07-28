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
 * @Description: 比赛
 * @Author: jeecg-boot
 * @Date:   2020-07-10
 * @Version: V1.0
 */
@Data
@TableName("playball_games")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_games对象", description="比赛")
public class Game {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**用整形来表示赛事的阶段，初始值0*/
	@Excel(name = "用整形来表示赛事的阶段，初始值0", width = 15)
    @ApiModelProperty(value = "用整形来表示赛事的阶段，初始值0")
	private Integer stage;
	/**sportsId*/
	@Excel(name = "sportsId", width = 15)
    @ApiModelProperty(value = "sportsId")
	private Integer sportsId;
	/**fieldId*/
	@Excel(name = "fieldId", width = 15)
    @ApiModelProperty(value = "fieldId")
	private Integer fieldId;
	/**gamesName*/
	@Excel(name = "gamesName", width = 15)
    @ApiModelProperty(value = "gamesName")
	private String gamesName;
	/**gamesInfo*/
	@Excel(name = "gamesInfo", width = 15)
    @ApiModelProperty(value = "gamesInfo")
	private Object gamesInfo;
	/**enrollTime*/
	@Excel(name = "enrollTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "enrollTime")
	private Date enrollTime;
	/**startTime*/
	@Excel(name = "startTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "startTime")
	private Date startTime;
	/**endTime*/
	@Excel(name = "endTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "endTime")
	private Date endTime;
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

	private String gameImage;
	private String gameRule;
}
