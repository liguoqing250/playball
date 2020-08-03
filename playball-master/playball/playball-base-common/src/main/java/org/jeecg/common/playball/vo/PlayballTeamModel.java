package org.jeecg.common.playball.vo;

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
public class PlayballTeamModel {
    
	private java.lang.Integer teamId;
	/**队名*/
	@JsonProperty("tName") 
	private java.lang.String tName;
	/**队徽（图片一张）*/
	@JsonProperty("tImage")
	private java.lang.String tImage;
	/**所属单位名*/
	@JsonProperty("tUnitname")
	private java.lang.String tUnitname;
	@JsonProperty("stId")
	/**球队类型(关联运动类型表)*/
	private java.lang.Integer stId;
	/**当前球队人数*/
	@JsonProperty("tPlayersTotal")
	private java.lang.Integer tPlayersTotal;
	/**当前球队最大人数*/
	@JsonProperty("tPlayersMax")
	private java.lang.Integer tPlayersMax;
	/**球队介绍 招募说明*/
	@JsonProperty("tIntroduce")
	private java.lang.String tIntroduce;
	/**胜 次数*/
	@JsonProperty("tWinTotal")
	private java.lang.Integer tWinTotal;
	/**平 次数*/
	@JsonProperty("tDivideTotal")
	private java.lang.Integer tDivideTotal;
	/**负 次数*/
	@JsonProperty("tLostTotal")
	private java.lang.Integer tLostTotal;
	/**球队创建人*/
	@JsonProperty("uId")
	private java.lang.Integer uId;
	/**所属省--对应地区表主键*/
	@JsonProperty("tProvince")
	private java.lang.Integer tProvince;
	/**所属市--对应地区表主键*/
	@JsonProperty("tCity")
	private java.lang.Integer tCity;
	/**所属区--对应地区表主键*/
	@JsonProperty("tDistrict")
	private java.lang.Integer tDistrict;
	/**建队时间*/
	@JsonProperty("tCreateteamtime")
	private java.util.Date tCreateteamtime;
	/**队长 球员表ID  p_id*/
	@JsonProperty("tCaptain")
	private java.lang.Integer tCaptain;
	/**创建人*/
	@JsonProperty("userName")
	private java.lang.String userName;
	/**队长姓名*/
	@JsonProperty("captainName")
	private java.lang.String captainName;
	/**运动类型名*/
	@JsonProperty("sportsName")
	private java.lang.String sportsName;
	/**进攻能力*/
	private java.lang.Integer attackValue;
	/**防守能力*/
	private java.lang.Integer defenseValue;
	/**综合能力*/
	private java.lang.Integer uniteValue;

}
