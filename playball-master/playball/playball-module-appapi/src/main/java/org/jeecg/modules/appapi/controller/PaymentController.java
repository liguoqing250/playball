package org.jeecg.modules.appapi.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.FieldReserveInfo;
import org.jeecg.modules.appapi.entity.WxPayment;
import org.jeecg.modules.appapi.entity.WxWithdraw;
import org.jeecg.modules.appapi.entity.vo.PaymentVo;
import org.jeecg.modules.appapi.mapper.FieldReserveInfoMapper;
import org.jeecg.modules.appapi.service.PaymentService;
import org.jeecg.modules.appapi.service.impl.WxPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;

@RestController
public class PaymentController {
	
	@Autowired
	private PaymentService payService;
	
	@Autowired
	private FieldReserveInfoMapper f_mapper;
	
	@Autowired
	private WxPayServiceImpl wxPayService; //微信提现
	
	/*微信响应预付单*/
	@PostMapping(value="/getAdvancedOrder")
	public Result<JSONObject> getAdvancedOrder(@RequestBody WxPayment wxPay) throws Exception {
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject obj = new JSONObject();
		System.err.println("前端数据" + wxPay);
		//后台查询数据相对前端安全,并赋值预下单对象 充值时跳过步骤
		if(wxPay.getGoods_id()!=null){
			BigDecimal price = getGoodsPrice(wxPay);
			wxPay.setTotal_fee(price);
		}
		
		//1、调用微信支付接口
		Map<String, String> prePay = payService.weixinPrePay(wxPay);
		
		//2、预付下单成功封装对象
		if(!prePay.isEmpty()){
			//封装对象返回
			PaymentVo Vo = new PaymentVo(prePay.get("appid"), prePay.get("partnerId"), prePay.get("prepayId"), prePay.get("package"), prePay.get("nonceStr"), prePay.get("timeStamp"), prePay.get("sign"));
			//设置随机订单返给前端
			Vo.setOrders(wxPay.getOut_trade_no());
			obj.put("data", Vo);
			obj.put("code", 200);
			result.setResult(obj);
		}else {
			obj.put("code", 0);
			result.setResult(obj);
		}
		return result;
}
	
	/**支付成功回调函数*/
	@RequestMapping("/callbackAction")
	@ResponseBody
	public String payMentCallBack(String attach  ) {
		System.out.println("回调，支付成功！"+attach);
		return "success";
	}
	
	/**获取商品数据返回商品单价*/
	public BigDecimal getGoodsPrice(WxPayment wxPay){
		BigDecimal big =null;
		//查询场地预定价格
		if(wxPay.getGoods_type() == 1){
			FieldReserveInfo info = f_mapper.selectById(wxPay.getGoods_id());
			big = info.getFriPrice();
		}else {
			
		}
		return big;
	}
	
	/*微信提现*/
	/**String openid: 用户 openid 
	 * String money: 付款金额
	 * String ip: 付款服务器 IP 测试期间就是公司 IP 1.204.113.40
	 * String desc 描述，例如 Playball 提现*/
	@PostMapping("/wxWithdrawal")
	public Result<JSONObject> wxWithdrawal(WxWithdraw wxWit) {
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject obj = new JSONObject();
		//转账金额 真是开发转为实体类 指定字段例如
		System.err.println("用户提现" + wxWit);
		String money = ProcessingAmount(String.format("%.2f", wxWit.getWit_fee()));
		
		HashMap<String, Object> hashMap = wxPayService.wxSendWallet(wxWit.getU_openid(), money,wxWit.getIp(),wxWit.getDesc());
		System.err.println("status" + hashMap.get("status"));
		obj.put("data", hashMap);
		result.setResult(obj);
		return result;
	}
	
	 /**支付宝支付
	 * @throws AlipayApiException */
	@PostMapping("/alipayPlay")
    public Result<?> alipayPlay(@RequestBody WxPayment wxPay) throws AlipayApiException{
    	 Map<String, Object> zhifubaoPrePay = payService.zhifubaoPrePay(wxPay);
    	 return Result.ok(zhifubaoPrePay);
    }
    
    /** 支付宝异步通知回调 */
    @ResponseBody
    @RequestMapping("/notify")
    public String notify(HttpServletRequest request, HttpServletResponse response) {
        String notify = payService.notify(request, response);
        System.err.println("notify" + notify);
		return notify;
    }
	
	
    //元转分
    public String ProcessingAmount(String str){
		String replace = str.replace(".", ""); //原来数据
		String sub =replace; //新数据
		int ix = 0; //下标
		if( ix == replace.indexOf("0")){
			for(int i = 0; i<replace.length();i++){
				int index = sub.indexOf("0");
				if(index == 0){
					sub = replace.substring(i);
				}
			}
			return sub;
		}else{
			return replace;
		}
	}
	
}
