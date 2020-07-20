package org.jeecg.modules.appapi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import org.jeecg.modules.appapi.entity.WxPayment;
import org.jeecg.modules.appapi.utils.PayCommonUtil;
import org.springframework.stereotype.Service;

/*支付统一下单接口*/
@Service
public class PaymentService {
	
	
	public Map<String, String> weixinPrePay (WxPayment wxPay){
		  Map<String,String> returnMap = new HashMap<>();
          //预支付
      	  String NONCE_STR=PayCommonUtil.getRandomString(32); //32位随机字符串
      	//订单金额转换为微信支付的分
  		String total_fee = ProcessingAmount(String.format("%.2f", wxPay.getTotal_fee()));
  		System.err.println("total_fee : " + total_fee);
  		
      	  SortedMap<String, Object> parameterMap = new TreeMap<String, Object>();  
          parameterMap.put("appid", wxPay.getAppId());  
          parameterMap.put("mch_id", wxPay.getMch_id());  
          parameterMap.put("nonce_str", NONCE_STR);  
          parameterMap.put("body", wxPay.getBody());
          parameterMap.put("out_trade_no", wxPay.getOut_trade_no());
          parameterMap.put("total_fee", total_fee); 
          parameterMap.put("spbill_create_ip", wxPay.getSpbill_create_ip());  
          parameterMap.put("notify_url", wxPay.getNotify_url());
          parameterMap.put("trade_type", "APP");
          parameterMap.put("sign_type", "MD5");
          parameterMap.put("attach", wxPay.getAttach());//附加数据
          
          //第一次获取MD5加密签名
          String sign = PayCommonUtil.createSign("UTF-8", parameterMap) ;
          parameterMap.put("sign", sign);
          
          //转换成 XML
          String requestXML = PayCommonUtil.getRequestXml(parameterMap);
          System.out.println("【转换为xml格式的参数】   "+requestXML);  
          
          //请求微信获取凭证
          String resultXML = PayCommonUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder","POST", requestXML); 
          System.out.println("【返回的XML】 " + resultXML);
          
          Map mapResult = PayCommonUtil.doXMLParse(resultXML);
          String returnCode = (String) mapResult.get("return_code");
          System.out.println("【返回内容   】   "+returnCode); 
          
         if("SUCCESS".equals(returnCode)){
             String resultCode = (String) mapResult.get("result_code");
             if("SUCCESS".equals(resultCode)){
                 String prepay_id = (String) mapResult.get("prepay_id");
                 
                 SortedMap<String, Object> finalpackage = new TreeMap<String, Object>();
                 finalpackage.put("appid", wxPay.getAppId());
                 finalpackage.put("partnerid", wxPay.getMch_id());
                 finalpackage.put("prepayid", prepay_id);
                 String noncestr = PayCommonUtil.getRandomString(32);
                 finalpackage.put("noncestr", noncestr);
                 String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                 finalpackage.put("timestamp", timestamp);
                 finalpackage.put("package", "Sign=WXPay");
                
                 sign = PayCommonUtil.createSign("UTF-8", finalpackage) ;
                 Map<String, String> returnmap = new HashMap<String, String>();
                 returnmap.put("appid", wxPay.getAppId());
                 returnmap.put("partnerId", wxPay.getMch_id() );
                 returnmap.put("prepayId", prepay_id);
                 returnmap.put("nonceStr", noncestr);
                 returnmap.put("timeStamp", timestamp);
                 returnmap.put("package", "Sign=WXPay");
                 returnmap.put("sign", sign);
                 return returnmap;
             }else{
                 String errCodeDes = (String) mapResult.get("err_code_des");
                 returnMap.put("errCodeDes", errCodeDes);
             }
         }else{
             String returnMsg = (String) mapResult.get("return_msg");
             returnMap.put("returnMsg", returnMsg);
         }
         return returnMap ;
 }
	
	/** 生成随机订单号。*/
	private static String getOrderStr() {
		return System.currentTimeMillis() +""+ ThreadLocalRandom.current().nextInt(100,200);
	}
	
	/*处理订单金额 BigDecimal 转换为 String 分*/
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
