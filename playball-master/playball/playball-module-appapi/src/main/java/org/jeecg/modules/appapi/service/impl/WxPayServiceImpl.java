package org.jeecg.modules.appapi.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.github.wxpay.sdk.WXPayUtil;

//微信提现接口
@Service
public class WxPayServiceImpl {

	private static final String TRANSFERS_PAY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	private static final String TRANSFERS_PAY_QUERY = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";

	private static final String APP_ID = "wx026beca713b85a9d";

	private static final String MCH_ID = "1544138361";
	
	private static final String APP_KEY = "YUZHICULTURE20190735GERENYUWZS30";
	
	
	public HashMap<String, Object> wxSendWallet(String openid, String money, String ip,String desc) {
		boolean tansferStatus = false;
		String responseInfo = "";
		String xml = wxSendData(openid, money, ip ,desc);
		try {
			// 指定读取证书格式为PKCS12
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File("D:\\Users\\liuerzxc\\Desktop\\playball_L\\apiclient_cert.p12"));
			System.out.println(instream);
			try {
				keyStore.load(instream, MCH_ID.toCharArray());
			} finally {
				instream.close();
			}
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, MCH_ID.toCharArray())
					.build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
			httppost.addHeader("Content-Type", "text/xml");
			StringEntity se = new StringEntity(xml, "UTF-8");
			httppost.setEntity(se);
			CloseableHttpResponse responseEntry = httpclient.execute(httppost);
			try {
				HttpEntity entity = responseEntry.getEntity();
				System.err.println("entity" + entity);
				if (entity != null) {
					SAXReader saxReader = new SAXReader();
					Document document = saxReader.read(entity.getContent());
					Element rootElt = document.getRootElement();
					String resultCode = rootElt.elementText("result_code");
					if (resultCode.equals("SUCCESS")) {
						tansferStatus = true;
						// 成功后操作
					} else {
						// 失败后操作
						responseInfo = rootElt.elementText("err_code_des");
					}
				}
				EntityUtils.consume(entity);
			} catch (Exception e) {

			} finally {
				responseEntry.close();
			}
		} catch (Exception e) {

		}
		if (tansferStatus == true) {}
		HashMap<String, Object> responseBody = new HashMap<>();
		responseBody.put("status", tansferStatus);
		responseBody.put("responseInfo", responseInfo);
		return responseBody;
	}
	
	//封装请求数据
	public static String wxSendData( String openid, String money, String ip ,String desc) {
        String data = null;
        try {
            String nonceStr = WXPayUtil.generateNonceStr();
            SortedMap<String,String> parameters = new TreeMap<String,String>();
            parameters.put("mch_appid",APP_ID );//商户账号appid
            parameters.put("mchid", MCH_ID);//商户号
            parameters.put("nonce_str", nonceStr);//随机字符串
            String outTradeNo = getOrderStr(); //随机生成
            parameters.put("partner_trade_no", outTradeNo); //商户订单号
            parameters.put("openid", openid); //用户 openid
            parameters.put("check_name", "NO_CHECK"); //NO_CHECK：不校验真实姓名  FORCE_CHECK：强校验真实姓名
            parameters.put("amount", money);
            parameters.put("spbill_create_ip",  ip); //Ip地址 非必须
            parameters.put("desc", desc);//备注
            //签名
            parameters.put("sign", WXPayUtil.generateSignature(parameters, APP_KEY));
            data = WXPayUtil.mapToXml(parameters);
            System.out.println("【data的数据 】");
            System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    
    /** 生成随机订单号。*/
	private static String getOrderStr() {
		return System.currentTimeMillis() +""+ ThreadLocalRandom.current().nextInt(100,200);
	}
}
