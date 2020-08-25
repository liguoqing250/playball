package org.jeecg.modules.appapi.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.modules.appapi.entity.AlipayConfig;
import org.jeecg.modules.appapi.entity.WxPayment;
import org.jeecg.modules.appapi.utils.PayCommonUtil;
import org.springframework.stereotype.Service;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

/*支付统一下单接口*/
@Service
public class PaymentService {
	
	/*微信支付*/
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
	
	/*支付宝支付*/
	public Map<String, Object> zhifubaoPrePay(WxPayment wxPay) throws AlipayApiException{
        Map<String, Object> map = new HashMap<>();
        //构造下单参数 client
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        //设置网关地址
        certAlipayRequest.setServerUrl(AlipayConfig.url);
        //设置应用Id
        certAlipayRequest.setAppId(AlipayConfig.app_id);
        //设置应用私钥
        certAlipayRequest.setPrivateKey(AlipayConfig.private_key);
        //设置请求格式，固定值json
        certAlipayRequest.setFormat(AlipayConfig.format);
        //设置字符集
        certAlipayRequest.setCharset(AlipayConfig.charset);
        //设置签名类型
        certAlipayRequest.setSignType(AlipayConfig.signtype);
        //设置应用公钥证书路径
        certAlipayRequest.setCertPath(AlipayConfig.app_cert_path);
        //设置支付宝公钥证书路径
        certAlipayRequest.setAlipayPublicCertPath(AlipayConfig.alipay_cert_path);
        //设置支付宝根证书路径
        certAlipayRequest.setRootCertPath(AlipayConfig.alipay_root_cert_path);
        //构造client
        AlipayClient alipayClient = new DefaultAlipayClient(certAlipayRequest);
        //订单号
        String out_trade_no = wxPay.getOut_trade_no();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(wxPay.getBody());//商品名称
        model.setBody(wxPay.getAttach());//商品信息
        model.setOutTradeNo(out_trade_no);//订单号
        model.setTotalAmount(String.valueOf(wxPay.getTotal_fee().doubleValue()));// 支付金额
        model.setTimeoutExpress("30m");//支付超时时间
        request.setBizModel(model);
        // 回调地址(充值订单)
        request.setNotifyUrl(AlipayConfig.notify_url);// 回调地址
        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = null;
        try {
            response = alipayClient.sdkExecute(request);
            map.put("code", "200");
            map.put("msg", "支付成功");
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("payPath", response.getBody());
            map.put("data", dataMap);
            map.put("orders", out_trade_no);
            return map;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        map.put("code", "0");
        map.put("msg", "支付失败");
        return map;
    }
	
	/**@Function: 支付宝异步回调接口*/
	public String notify(HttpServletRequest request, HttpServletResponse response) {
		System.err.println("------------支付宝异步回调接口------------");
        Map<String, String> params = new HashMap<String, String>();
        //从支付宝回调的request域中取值
        Map<String, String[]> requestParams = request.getParameterMap();
 
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        System.err.println("requestParams" + requestParams);
        System.err.println("params" + params);
        //商品订单号
        String out_trade_no = request.getParameter("out_trade_no");
        // 当前交易状态
        String tradeStatus = request.getParameter("trade_status");
        // 支付金额
        String totalAmount = request.getParameter("total_amount");   
        // 支付时间
        String payDate = request.getParameter("gmt_payment");
        //3.签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {
            //3.1调用SDK验证签名 rsaCheckV1普通验证  rsaCertCheckV1 证书验证
            signVerified = AlipaySignature.rsaCertCheckV1(params, AlipayConfig.alipay_cert_path, AlipayConfig.charset, AlipayConfig.signtype);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //对验签进行处理
        if (signVerified) {
            //验签通过
            if(tradeStatus.equals("TRADE_SUCCESS")) {
                //支付成功后的业务处理
            	System.out.println(Long.valueOf(out_trade_no));
                return "success";
            }
        } else {  //验签不通过
            System.err.println("验签失败");
            return "failure";
        }
        return "failure";
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
