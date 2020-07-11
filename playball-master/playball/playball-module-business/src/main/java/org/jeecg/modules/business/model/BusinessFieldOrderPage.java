package org.jeecg.modules.business.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 场地信息
 * @Author: 
 * @Date:  
 * @Version: V1.0
 */
@Data
@TableName("")
public class BusinessFieldOrderPage implements Serializable {
private static final long serialVersionUID = 1L;
    
	/**主键*/
    @TableId(type = IdType.AUTO)
	private java.lang.Integer id;
    
	/**时间范围*/
	private java.lang.String timeRanges ;
	
	/**预定日期*/
	private java.util.Date  reserveDate;
	
	/**运动类型*/
	private java.lang.String sportsId;
	
	/**场地id*/
	private java.lang.Integer fieldId;
	
	/**场地价格*/
	private java.lang.Double totalPrice;
	
	/**球队id*/
	private java.lang.Integer teamId;
	
	/**用户id*/
	private java.lang.Integer userId;
	
	/**下单时间*/
	private java.util.Date OrderTime;
	
	/**运动名称*/
	private java.lang.String sportsName;
	
	/**用户名称*/
	private java.lang.String userName;
	
	/**场地名称*/
	private java.lang.String fieldName;
	
	/**球队名称*/
	private java.lang.String teamName;
	
	/**商户id*/
	private java.lang.String businessId;
	
	/**商户（场馆）名称*/
	private java.lang.Integer businessName;
}
