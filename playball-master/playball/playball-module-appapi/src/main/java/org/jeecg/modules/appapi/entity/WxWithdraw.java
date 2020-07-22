package org.jeecg.modules.appapi.entity;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/*微信提现实体类*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WxWithdraw {
	private String u_openid ;//用户Openid
	private BigDecimal wit_fee ; //提现金额
	private String ip="1.204.116.200";//终端ip
	private String desc = "用户提现";//描述，例如 Playball 提现
}
