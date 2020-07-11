package org.jeecg.modules.appapi.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.net.ssl.SSLContext;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import javax.net.ssl.HttpsURLConnection;

/*微信支付工具类*/
public class PayCommonUtil {

	/** post请求并得到返回结果 */
	public static String httpsRequest21(String requestUrl, String requestMethod, String output) {
		try {
			URL url = new URL(requestUrl);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod(requestMethod);
			if (null != output) {
				OutputStream outputStream = connection.getOutputStream();
				outputStream.write(output.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			connection.disconnect();
			return buffer.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	// 随机字符串生成
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// 请求xml组装
	public static String getRequestXml(SortedMap<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();

		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			// String value = (String) entry.getValue();
			Object value = entry.getValue();
			if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
				sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
			} else {
				sb.append("<" + key + ">" + value + "</" + key + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	// 生成签名
	public static String createSign(String characterEncoding, SortedMap<String, Object> parameterMap) {
		if (parameterMap == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		List<String> keys = new ArrayList<>(parameterMap.keySet());
		Collections.sort(keys);

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			Object value = parameterMap.get(key);
			sb.append((i == 0 ? "" : "&") + key + "=" + value);
		}
		sb.append("&key=" + "YUZHICULTURE20190735GERENYUWZS30");
		System.err.println("【生成签名   】" + sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}

	// 微信支付请求
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();
			return buffer.toString();
		} catch (ConnectException ce) {
			System.out.println("连接超时：" + ce);
		} catch (Exception e) {
			System.out.println("https请求异常" + e);
		}
		return null;
	}
	 // 退款的请求方法
	     public static String httpsRequest2(String requestUrl, String requestMethod, String outputStr) throws Exception {
	         KeyStore keyStore = KeyStore.getInstance("PKCS12");
	         StringBuilder res = new StringBuilder("");
	         FileInputStream instream = new FileInputStream(new File("/home/apiclient_cert.p12"));
	         try {
	             keyStore.load(instream, "".toCharArray());
	         } finally {
	             instream.close();
	         }
	 
	         SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "1313329201".toCharArray()).build();
	         SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
	                 SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	         CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	         try {
	 
	             HttpPost httpost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
	             httpost.addHeader("Connection", "keep-alive");
	             httpost.addHeader("Accept", "*/*");
	             httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	             httpost.addHeader("Host", "api.mch.weixin.qq.com");
	             httpost.addHeader("X-Requested-With", "XMLHttpRequest");
	             httpost.addHeader("Cache-Control", "max-age=0");
	             httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
	             StringEntity entity2 = new StringEntity(outputStr, Consts.UTF_8);
	             httpost.setEntity(entity2);
	             System.out.println("executing request" + httpost.getRequestLine());
	 
	             CloseableHttpResponse response = httpclient.execute(httpost);
	 
	             try {
	                 HttpEntity entity = response.getEntity();
	 
	                 System.out.println("----------------------------------------");
	                 System.out.println(response.getStatusLine());
	                 if (entity != null) {
	                     System.out.println("Response content length: " + entity.getContentLength());
	                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
	                     String text = "";
	                     res.append(text);
	                     while ((text = bufferedReader.readLine()) != null) {
	                         res.append(text);
	                         System.out.println(text);
	                     }
	 
	                 }
	                 EntityUtils.consume(entity);
	             } finally {
	                 response.close();
	             }
	         } finally {
	             httpclient.close();
	         }
	         return res.toString();
	 
	     }
	 
     // xml解析
     public static SortedMap<String, Object> doXMLParse(String strxml){
         strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
         if (null == strxml || "".equals(strxml)) {
             return null;
         }
         SortedMap<String, Object> m = new TreeMap<>();
 
         InputStream in;
         try {
             in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
             SAXBuilder builder = new SAXBuilder();
             Document doc = builder.build(in);
             Element root = doc.getRootElement();
             List list = root.getChildren();
             Iterator it = list.iterator();
             while (it.hasNext()) {
                 Element e = (Element) it.next();
                 String k = e.getName();
                 String v = "";
                 List children = e.getChildren();
                 if (children.isEmpty()) {
                     v = e.getTextNormalize();
                 } else {
                     v = getChildrenText(children);
                 }
 
                 m.put(k, v);
             }
 
             // 关闭流
             in.close();
         } catch (Exception e1) {
             e1.printStackTrace();
         }
         return m;
     }
	 
     public static String getChildrenText(List children) {
         StringBuffer sb = new StringBuffer();
         if (!children.isEmpty()) {
             Iterator it = children.iterator();
             while (it.hasNext()) {
                 Element e = (Element) it.next();
                 String name = e.getName();
                 String value = e.getTextNormalize();
                 List list = e.getChildren();
                 sb.append("<" + name + ">");
                 if (!list.isEmpty()) {
                     sb.append(getChildrenText(list));
                 }
                 sb.append(value);
                 sb.append("</" + name + ">");
             }
         }
         return sb.toString();
     }
}
