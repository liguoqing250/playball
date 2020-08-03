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
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 球队查看
 * @Author: jeecg-boot
 * @Date:   2020-07-23
 * @Version: V1.0
 */
@Data
@TableName("playball_team")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_team对象", description="球队查看")
public class PlayballTeam implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	/**teamId*/
	@TableId(type = IdType.AUTO)
	@Excel(name = "teamId", width = 15)
    @ApiModelProperty(value = "teamId")
	private java.lang.Integer teamId;
	/**队名*/
	@Excel(name = "队名", width = 15)
    @ApiModelProperty(value = "队名")
	@JsonProperty("tName") 
	private java.lang.String tName;
	/**队徽（图片一张）*/
	@Excel(name = "队徽（图片一张）", width = 15)
    @ApiModelProperty(value = "队徽（图片一张）")
	@JsonProperty("tImage") 
	private java.lang.String tImage;
	/**所属单位名*/
	@Excel(name = "所属单位名", width = 15)
    @ApiModelProperty(value = "所属单位名")
	@JsonProperty("tUnitname") 
	private java.lang.String tUnitname;
	/**球队类型(关联运动类型表)*/
	@Excel(name = "球队类型(关联运动类型表)", width = 15)
    @ApiModelProperty(value = "球队类型(关联运动类型表)")
	private java.lang.Integer stId;
	/**当前球队人数*/
	@Excel(name = "当前球队人数", width = 15)
    @ApiModelProperty(value = "当前球队人数")
	@JsonProperty("tPlayersTotal") 
	private java.lang.Integer tPlayersTotal;
	/**当前球队最大人数*/
	@Excel(name = "当前球队最大人数", width = 15)
    @ApiModelProperty(value = "当前球队最大人数")
	@JsonProperty("tPlayersMax") 
	private java.lang.Integer tPlayersMax;
	/**球队介绍 招募说明*/
	@Excel(name = "球队介绍 招募说明", width = 15)
    @ApiModelProperty(value = "球队介绍 招募说明")
	@JsonProperty("tIntroduce") 
	private java.lang.String tIntroduce;
	/**胜 次数*/
	@Excel(name = "胜 次数", width = 15)
    @ApiModelProperty(value = "胜 次数")
	@JsonProperty("tWinTotal") 
	private java.lang.Integer tWinTotal;
	/**平 次数*/
	@Excel(name = "平 次数", width = 15)
    @ApiModelProperty(value = "平 次数")
	@JsonProperty("tDivideTotal") 
	private java.lang.Integer tDivideTotal;
	/**负 次数*/
	@Excel(name = "负 次数", width = 15)
    @ApiModelProperty(value = "负 次数")
	@JsonProperty("tLostTotal") 
	private java.lang.Integer tLostTotal;
	/**球队创建人*/
	@Excel(name = "球队创建人", width = 15)
    @ApiModelProperty(value = "球队创建人")
	@JsonProperty("uId") 
	private java.lang.Integer uId;
	/**所属省--对应地区表主键*/
	@Excel(name = "所属省--对应地区表主键", width = 15)
    @ApiModelProperty(value = "所属省--对应地区表主键")
	@JsonProperty("tProvince") 
	private java.lang.Integer tProvince;
	/**所属市--对应地区表主键*/
	@Excel(name = "所属市--对应地区表主键", width = 15)
    @ApiModelProperty(value = "所属市--对应地区表主键")
	@JsonProperty("tCity") 
	private java.lang.Integer tCity;
	/**所属区--对应地区表主键*/
	@Excel(name = "所属区--对应地区表主键", width = 15)
    @ApiModelProperty(value = "所属区--对应地区表主键")
	@JsonProperty("tDistrict") 
	private java.lang.Integer tDistrict;
	/**进攻能力*/
	@Excel(name = "进攻能力", width = 15)
	private java.lang.Integer attackValue;
	/**防守能力*/
	@Excel(name = "防守能力", width = 15)
	private java.lang.Integer defenseValue;
	/**综合能力*/
	@Excel(name = "综合能力", width = 15)
	private java.lang.Integer uniteValue;
	/**建队时间*/
	@Excel(name = "建队时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "建队时间")
	@JsonProperty("tCreateteamtime") 
	private java.util.Date tCreateteamtime;
	/**队长 球员表ID  p_id*/
	@Excel(name = "队长 球员表ID  p_id", width = 15)
    @ApiModelProperty(value = "队长 球员表ID  p_id")
	@JsonProperty("tCaptain") 
	private java.lang.Integer tCaptain;
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
	private java.lang.Integer isDelete;
	/**版本号（用作乐观锁）*/
	@Excel(name = "版本号（用作乐观锁）", width = 15)
    @ApiModelProperty(value = "版本号（用作乐观锁）")
	private java.lang.Integer version;
}
