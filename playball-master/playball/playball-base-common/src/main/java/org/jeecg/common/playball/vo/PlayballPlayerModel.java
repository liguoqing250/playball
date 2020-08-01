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
 * @Description: 队员管理
 * @Author: jeecg-boot
 * @Date:   2020-07-24
 * @Version: V1.0
 */
@Data
public class PlayballPlayerModel {

	private java.lang.Integer tpId;

	private java.lang.Integer uId;

	private java.util.Date tpJointime;

	private java.util.Date tpQuittime;

	private java.lang.Integer teamId;

	private java.lang.Integer tpClothesNumber;

	private java.lang.Integer tpPosition;
	
	private java.util.Date createtime;

	private java.util.Date updatetime;

	private java.lang.String isDelete;

	private java.lang.String version;
	
	private java.lang.String userName;
	
	private java.lang.String nickName;
	
	private java.lang.String headImage;
	
	private java.lang.String teamName;
	
	private java.lang.String positionName;
	
	private java.lang.Integer sportsId;
	
	private java.lang.String abilityValue;
	
	private java.lang.String dataJson;
	
	private java.lang.Integer scheduleUserDataId;
}
