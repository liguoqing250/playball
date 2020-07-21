package org.jeecg.common.playball.vo;

import lombok.Data;


/**
 * @Description: 订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
@Data
public class PlayballOrderModel {
    
	private java.lang.Integer ordId;

	private java.lang.Integer ordUserId;

	private java.lang.Integer ordGoodsId;

	private java.lang.String ordGoodsName;
	
	private java.math.BigDecimal cprPrice;

	private java.lang.String cprNumber;
	/**订单类型：1、场地预定 2、视频购买*/
	private java.lang.Integer cprType;
	
	/**订单状态：1、已支付 2、未支付*/
	private java.lang.String cprState;
	
	private java.lang.String userName;
	
	//商家信息
	private java.lang.String businessId;
	
	private java.lang.String businessName;
}
