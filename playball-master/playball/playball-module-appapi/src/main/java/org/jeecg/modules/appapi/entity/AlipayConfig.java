package org.jeecg.modules.appapi.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/*封装支付宝支付实体类*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AlipayConfig {
	// APPID
    public static String app_id = "2021001188691525";
 
    // 对应的私钥
    public static String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCtAjMUUlVbhvOERrlFsWTpjMnHY1wKFUz55QCgtpH04DlIlV+JHfEMOqeydC21wLjngWavsYZ4rPcMXW9imi0LUvtTtSaNYkQ+ItSUaQBI34VlvCw2J78fqiS1Z99XzR6eJuDR/e3KLwd7rqJhAqtwElQ2P56gRs9G0NrfK88TwbhyLQTkTq5//LaHb82IDM6YRii8UJzuigQ8pBTrJYnL4n5gLcBGJVpHkXQgs5lfa941A/wfj/vjzFz+j4yZRzfo/KBQjsGiXAKixHzYJa6zDWUR4eWuFaWuXdXawSRq/uzYGWcBDbZ6nYOUrjChbWQKx/4+t3nTSswSwYJvAJMXAgMBAAECggEAeFtHXaPJbdr8HdF1ol4+bZtb5DHjHcCjxFTjkupvr4MtVqlTxPfncTJ851CbXA2ulJ8Y6LD/c4QgeaGmk1nebDpWTW2AXGs4PmHMsVI6uHeHPbciqqoL8+NRlkGkxlgULMGvNWcJvdXpxsk+fW52BYk6vGKV37pjCEjTet3nOT4r1ddo3JqsWbGw0p9v/lX0wjlEuP2GTbJgS0n7DuxQ0Ni5serGYwD6Fvx7p4UIG9CXMyzmYVeyl3Aa7exeVdJfpvzU8H1zvQzeRjyqfBbphkqFI60yEQoZWWP1Jg0WkxWLVopLpuAZ17GvXorKdOc0LOrhXOjw4ZfNHmrqPNBvAQKBgQDv6PbItbrraOkVsiMbpk5ZPdJpQjWUyQeETnAScv+wII1YB1SSadpkCA4ULzTK/+4WlaJT3UhCXs54iCN0UCTiCirlcrGQiIQwiRhP4g/sK9JF/V06gXhl3XMPrY04ZEJhTE3h7s6/6vP2Io07c9IWXRw7/pXcX4FO3NEqL0iXBwKBgQC4nJlhk/Ewzjon1sfOayqQWW4YC/5gcQRJDRhwHUcjODGPCocEueM2cHBy1Q6ZAMuzBytADdMAAQvN7Hnzh1g1ea3Fkpv9KMDNi8DVuNnZiwbFKP0cvgrXT+vnw/y76nVmgkkDsdOClBbwOVZoOGxPljpYVHeayaurWoxwSF+PcQKBgG7f6622zzwcsBwmx7LvmSYFPEkJIFoyzhaHaaQdIa30XV5hhORlp1izoE4TwwJOZWpqnSrFCbAiwKlTwY7vuxv8NvbzmryBWxZTRbklcNZlKjjOb1eqnkn1nkeQncOCEmuFKDlSdJqdq0IFld/0FxPe1D2l2vyN5uTbiI9GavXhAoGAeJVZHkJa3U0m8VS6J9sZ7zVq0uwqmGXZrbmPB9Qn3hpbTJpMvMqKXcmtuWPu6AN8ChEZr/oXoz4LrtLSiT4TYMJ8vbtYhlBzNw8cwry+FvlXT7rJMVf0jtTWOwlp8HhhfLaG6JHjnLwMC5b4GJUaMhXnQEDT6tfleMRpTQII4RECgYEA2tPDHCXBOpNJAgpjGo11/g4aA6fmM7M00S5BHTKHRMVMK4sad1sH2nzdPYA2kgJgbg6MCCU/a9gv1wji/J3cmVHIvpkZEkR+Anbd/c/pOdlBZBmRbk+ZKxRA5PSjpS+1UJqk/3QAl5/ZzOAp7XukUzMk7E+T43KpKNZcCSINsUk=";
    
    // 对应的公钥
    public static String public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArQIzFFJVW4bzhEa5RbFk6YzJx2NcChVM+eUAoLaR9OA5SJVfiR3xDDqnsnQttcC454Fmr7GGeKz3DF1vYpotC1L7U7UmjWJEPiLUlGkASN+FZbwsNie/H6oktWffV80enibg0f3tyi8He66iYQKrcBJUNj+eoEbPRtDa3yvPE8G4ci0E5E6uf/y2h2/NiAzOmEYovFCc7ooEPKQU6yWJy+J+YC3ARiVaR5F0ILOZX2veNQP8H4/748xc/o+MmUc36PygUI7BolwCosR82CWusw1lEeHlrhWlrl3V2sEkav7s2BlnAQ22ep2DlK4woW1kCsf+Prd500rMEsGCbwCTFwIDAQAB";
 
    //异步回调接口:得放到服务器上，且使用域名解析 IP
    public static String notify_url = "http://go23760150.qicp.vip:23228/notify";
 
    //支付宝网关（沙箱alipaydev，正式则为 alipay）不需要修改
    public static String url = "https://openapi.alipay.com/gateway.do";
 
    //编码类型
    public static String charset = "utf-8";
 
    //数据类型
    public static String format = "json";
 
    //签名类型
    public static String signtype = "RSA2";
    
    //设置应用公钥证书路径
    public static String app_cert_path = "D:\\PlayBall\\appCertPublicKey.crt";
    
    //设置支付宝公钥证书路径
    public static String alipay_cert_path = "D:\\PlayBall\\alipayCertPublicKey_RSA2.crt";
    
    //设置支付宝根证书路径
    public static String alipay_root_cert_path = "D:\\PlayBall\\alipayRootCert.crt";
    
    
}
