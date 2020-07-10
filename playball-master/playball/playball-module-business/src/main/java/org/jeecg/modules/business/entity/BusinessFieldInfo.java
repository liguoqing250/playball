package org.jeecg.modules.business.entity;

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
@TableName("playball_field_info")
public class BusinessFieldInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
    @TableId(type = IdType.AUTO)
	private java.lang.Integer id;
    
	/**场地名*/
	private java.lang.String fieldName;
	
	/**运动类型*/
	private java.lang.String sportsId;
	
	/**商户id*/
	private java.lang.String businessId;
	
	/**场地价格*/
	private java.lang.Double fieldPrice;
	
	/**图片地址*/
	private java.lang.String imageUrl;
	
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	
	/***/
	private java.lang.String version;
	
	/**修改时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	
	private java.lang.String delFlag;
}
