package org.jeecg.common.playball.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;


@Data
public class PlayballFieldInfoModel {
	
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
	
	private java.lang.String sportsName;
	
	private java.lang.String businessName;

	
}
