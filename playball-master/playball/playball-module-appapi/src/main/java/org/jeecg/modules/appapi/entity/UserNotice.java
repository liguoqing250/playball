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
 * @Description: 消息
 * @Author: jeecg-boot
 * @Date:   2020-08-17
 * @Version: V1.0
 */
@Data
@TableName("playball_user_notice")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="playball_user_notice对象", description="消息")
public class UserNotice {
    
	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
	private Integer id;
	/**消息类型：1、系统 2、球队3、回复、4、比赛*/
	@Excel(name = "消息类型：1、系统 2、球队3、回复、4、比赛", width = 15)
    @ApiModelProperty(value = "消息类型：1、系统 2、球队3、回复、4、比赛")
	private Integer type;
	/**文本内容*/
	@Excel(name = "文本内容", width = 15)
    @ApiModelProperty(value = "文本内容")
	private String content;
	/**接收人*/
	@Excel(name = "接收人", width = 15)
    @ApiModelProperty(value = "接收人")
	private Integer receiverUid;
	/**关联数据id*/
	@Excel(name = "关联数据id", width = 15)
    @ApiModelProperty(value = "关联数据id")
	private Integer belongId;
	/**报名时间*/
	@Excel(name = "报名时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "报名时间")
	private Date createTime;
	/**updateTime*/
	@Excel(name = "updateTime", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
	private Date updateTime;
	/**删除表示*/
	@Excel(name = "删除表示", width = 15)
    @ApiModelProperty(value = "删除表示")
	private Integer delFlg;
	/**verison*/
	@Excel(name = "verison", width = 15)
    @ApiModelProperty(value = "verison")
	private Integer verison;
}
