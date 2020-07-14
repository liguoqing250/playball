package org.jeecg.modules.appapi.entity;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/*封装微信支付实体类*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WxPayment {
	private String appId = "wx026beca713b85a9d";
	private String mch_id = "1544138361";
	
	private String total_fee ="1" ; //支付金额
	private String body ="暂无描述" ;//商品描述
	private String out_trade_no = getOrderStr();//商户订单号
	private String attach = "暂无附加数据";//附加数据
	
	private String spbill_create_ip="47.108.52.77";//终端ip
	private String notify_url ="https://playball.yuzhiculture.com/callbackAction";//回调地址

	/** 生成随机订单号。*/
	private  String getOrderStr() {
		return System.currentTimeMillis() +""+ ThreadLocalRandom.current().nextInt(100,200);
	}
}
