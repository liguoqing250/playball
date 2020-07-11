package org.jeecg.modules.appapi.entity.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentVo {
	private String appid; //appid
	private String partnerid; //商户号
	private String prepayid; 
	private String pacKage;
	private String nonceStr; 
	private String timeStamp;
	private String sign;
	private String orders; //订单号
	public PaymentVo(String appid, String partnerid, String prepayid, String pacKage, String nonceStr, String timeStamp,
			String sign) {
		super();
		this.appid = appid;
		this.partnerid = partnerid;
		this.prepayid = prepayid;
		this.pacKage = pacKage;
		this.nonceStr = nonceStr;
		this.timeStamp = timeStamp;
		this.sign = sign;
	}
}
