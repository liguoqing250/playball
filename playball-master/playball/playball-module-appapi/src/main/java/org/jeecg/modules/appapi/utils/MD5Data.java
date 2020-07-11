package org.jeecg.modules.appapi.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Data {
	public static String code(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static String md5_32(String str)   {
		String rs = null;
		try {
			rs= md5(str.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	private static String md5(byte [] srcbyte) throws NoSuchAlgorithmException {
		MessageDigest dg = MessageDigest.getInstance("md5");
		byte [] rs = dg.digest(srcbyte);
		return bytesToStr(rs);
	}
	
	private static String bytesToStr(byte []bs) {
		StringBuilder sb = new StringBuilder();
		for(byte b:bs) {
			int a = b<0 ? 256 + b : b;//
			String str = Integer.toHexString(a);
			if(a<16) {
				sb.append("0");
			}
			sb.append(str);
		}
		 return sb.toString();
	}
	
	
}
