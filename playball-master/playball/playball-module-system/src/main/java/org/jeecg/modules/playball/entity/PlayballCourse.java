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
 * @Description: cms教程
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Data
@TableName("playball_curriculum")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_curriculum对象", description="cms教程")
public class PlayballCourse implements Serializable  {
	private static final long serialVersionUID = 1L;
	
	/**主键id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "c_id")
	private java.lang.Integer cId;
	/**教程标题*/
	@Excel(name = "教程标题", width = 15)
    @ApiModelProperty(value = "教程标题")
	private java.lang.String cTitle;
	/**教程详情*/
	@Excel(name = "教程详情", width = 15)
    @ApiModelProperty(value = "教程详情")
	private java.lang.String cInfo;
	/**教程视频*/
	@Excel(name = "教程视频", width = 15)
    @ApiModelProperty(value = "教程视频")
	private java.lang.String cVideo;
	/**运动类型*/
	@Excel(name = "运动类型", width = 15)
    @ApiModelProperty(value = "运动类型")
	private java.lang.Integer sId;
	/**适龄范围1*/
	@Excel(name = "适龄范围1", width = 15)
    @ApiModelProperty(value = "适龄范围1")
	private java.lang.String cAgerange;
	/**是否付费*/
	@Excel(name = "是否付费", width = 15)
    @ApiModelProperty(value = "是否付费")
	private java.lang.Object cIsFree;
	/**价格*/
	@Excel(name = "价格", width = 15)
    @ApiModelProperty(value = "价格")
	private java.math.BigDecimal cPrice;
	/**点赞数*/
	@Excel(name = "点赞数", width = 15)
    @ApiModelProperty(value = "点赞数")
	private java.lang.Integer cFabulous;
	/**浏览数（后面根据这个查询热门视频）*/
	@Excel(name = "浏览数（后面根据这个查询热门视频）", width = 15)
    @ApiModelProperty(value = "浏览数（后面根据这个查询热门视频）")
	private java.lang.Integer cBrowse;
	/**优先级（后期扩展根据级别查询热门）*/
	@Excel(name = "优先级（后期扩展根据级别查询热门）", width = 15)
    @ApiModelProperty(value = "优先级（后期扩展根据级别查询热门）")
	private java.lang.Integer cFirst;
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
	/**逻辑删除*/
	@Excel(name = "逻辑删除", width = 15)
    @ApiModelProperty(value = "逻辑删除")
	private java.lang.Integer isDelete;
	/**版本号（用作乐观锁）*/
	@Excel(name = "版本号（用作乐观锁）", width = 15)
    @ApiModelProperty(value = "版本号（用作乐观锁）")
	private java.lang.Integer version;
}
