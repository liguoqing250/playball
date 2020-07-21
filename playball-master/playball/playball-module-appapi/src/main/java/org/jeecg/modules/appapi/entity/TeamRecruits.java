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
 * @Description: 招募信息
 * @Author: jeecg-boot
 * @Date:   2020-07-21
 * @Version: V1.0
 */
@Data
@TableName("playball_team_recruits")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_team_recruits对象", description="招募信息")
public class TeamRecruits {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**关联球队ID*/
	@Excel(name = "关联球队ID", width = 15)
    @ApiModelProperty(value = "关联球队ID")
	private Integer teamId;
	/**关联运动位置表*/
	@Excel(name = "关联运动位置表", width = 15)
    @ApiModelProperty(value = "关联运动位置表")
	private Integer position;
	/**招募说明*/
	@Excel(name = "招募说明", width = 15)
    @ApiModelProperty(value = "招募说明")
	private String explaininfo;
	/**招募人数*/
	@Excel(name = "招募人数", width = 15)
    @ApiModelProperty(value = "招募人数")
	private Integer maxplayers;
}
