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
 * @Description: 报名球队管理
 * @Author: jeecg-boot
 * @Date:   2020-07-17
 * @Version: V1.0
 */
@Data
@TableName("playball_enroll")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_enroll对象", description="报名球队管理")
public class PlayballEnroll implements Serializable  {
    
	private static final long serialVersionUID = 1L;
	
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**球队id*/
	@Excel(name = "球队id", width = 15)
    @ApiModelProperty(value = "球队id")
	private java.lang.Integer teamId;
	/**赛事id*/
	@Excel(name = "赛事id", width = 15)
    @ApiModelProperty(value = "赛事id")
	private java.lang.Integer gamesId;
	/**报名时间*/
	@Excel(name = "报名时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报名时间")
	private java.util.Date createTime;
	/**updateTime*/
	@Excel(name = "updateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
	private java.util.Date updateTime;
	/**删除表示*/
	@Excel(name = "删除表示", width = 15)
    @ApiModelProperty(value = "删除表示")
	private java.lang.Integer delFlg;
	/**verison*/
	@Excel(name = "verison", width = 15)
    @ApiModelProperty(value = "verison")
	private java.lang.Integer verison;
}
