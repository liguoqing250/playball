package org.jeecg.modules.appapi.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ThreadLocalRandom;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.appapi.entity.WxPayment;
import org.jeecg.modules.appapi.entity.vo.PaymentVo;
import org.jeecg.modules.appapi.service.PaymentService;
import org.jeecg.modules.appapi.utils.HttpUtils;
import org.jeecg.modules.appapi.utils.MD5Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;

@RestController
public class PaymentController {
	
	@Autowired
	private PaymentService payService;
	
	/*响应预付单*/
	@PostMapping(value="/getAdvancedOrder")
	public Result<JSONObject> getAdvancedOrder(@RequestBody WxPayment wxPay) throws Exception {
		Result<JSONObject> result = new Result<JSONObject>();
		JSONObject obj = new JSONObject();
		System.err.println("前端数据" + wxPay);
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
	
}
