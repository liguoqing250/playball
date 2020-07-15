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
 * @Description: 运动类型
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Data
@TableName("playball_sports_type")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_sports_type对象", description="运动类型")
public class PlayballSportType  implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	/**id*/
	/**主键id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**运动类名称*/
	@Excel(name = "运动类名称", width = 15)
    @ApiModelProperty(value = "运动类名称")
	private java.lang.String sportsName;
	
	/**纪录创建时间*/
	@Excel(name = "纪录创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "纪录创建时间")
	private java.util.Date createTime;
	/**纪录修改时间*/
	@Excel(name = "纪录修改时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "纪录修改时间")
	private java.util.Date updateTime;
	
	/**版本号（用作乐观锁）*/
	private java.lang.Integer version;
	
}
