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
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description: 用户动态发布
 * @Author: jeecg-boot
 * @Date:   2020-07-29
 * @Version: V1.0
 */
@Data
public class PlayballDynamicModel {
    

	private java.lang.Integer udyId;

	private java.lang.Integer udyUserId;

	private java.lang.String udyContent;
	
	private java.lang.String udyResources;

	private java.lang.Integer udyResType;

	private java.lang.String udyPosition;

	private java.lang.Integer udyVisible;

	private java.lang.Integer udyDraft;

	private java.util.Date createTime;

	private java.util.Date updateTime;

	private java.lang.Integer isDelete;

	private java.lang.Integer version;
	
	private java.lang.String userName;
	
	private java.lang.String nickName;
}
