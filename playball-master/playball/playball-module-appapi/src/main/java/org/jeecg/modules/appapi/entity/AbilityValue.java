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
 * @Description: 运动能力值
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
@Data
@TableName("playball_ability_value")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_ability_value对象", description="运动能力值")
public class AbilityValue {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**运动类型（关联运动类型表）*/
	@Excel(name = "运动类型（关联运动类型表）", width = 15)
    @ApiModelProperty(value = "运动类型（关联运动类型表）")
	private Integer sportsId;
	/**能力值最大值，默认100*/
	@Excel(name = "能力值最大值，默认100", width = 15)
    @ApiModelProperty(value = "能力值最大值，默认100")
	private Integer maxValue;
	/**能力值名称*/
	@Excel(name = "能力值名称", width = 15)
    @ApiModelProperty(value = "能力值名称")
	private String avName;
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
