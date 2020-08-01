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
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 资讯管理
 * @Author: jeecg-boot
 * @Date:   2020-08-01
 * @Version: V1.0
 */
@Data
@TableName("playball_news")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_news对象", description="资讯管理")
public class PlayballNews {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private java.lang.Integer id;
	/**标题*/
	@Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
	private java.lang.String title;
	/**封面图片*/
	@Excel(name = "封面图片", width = 15)
    @ApiModelProperty(value = "封面图片")
	private java.lang.String image;
	/**详细内容*/
	@Excel(name = "详细内容", width = 15)
    @ApiModelProperty(value = "详细内容")
	private java.lang.Object content;
	/**简述*/
	@Excel(name = "简述", width = 15)
    @ApiModelProperty(value = "简述")
	private java.lang.String introduction;
	/**评论量*/
	@Excel(name = "评论量", width = 15)
    @ApiModelProperty(value = "评论量")
	private java.lang.Integer commentTotal;
	/**浏览量*/
	@Excel(name = "浏览量", width = 15)
    @ApiModelProperty(value = "浏览量")
	private java.lang.Integer pageView;
	/**资讯类型*/
	@Excel(name = "资讯类型", width = 15)
    @ApiModelProperty(value = "资讯类型")
	private java.lang.Integer newsType;
	/**标签：1 图片 2、视频*/
	@Excel(name = "标签：1 图片 2、视频", width = 15)
    @ApiModelProperty(value = "标签：1 图片 2、视频")
	private java.lang.Integer label;
	/**创建时间*/
	@Excel(name = "创建时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;
	/**更新时间*/
	@Excel(name = "更新时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
	private java.util.Date updateTime;
	/**删除标志*/
	@Excel(name = "删除标志", width = 15)
    @ApiModelProperty(value = "删除标志")
	private java.lang.Integer delFlg;
	/**version*/
	@Excel(name = "version", width = 15)
    @ApiModelProperty(value = "version")
	private java.lang.Integer version;
}
