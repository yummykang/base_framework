package com.ehu.utils;

import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 算法工具类
 * 
 * @author chenlong 2015-12-17
 * 
 */
@Service
public class MathUtil {
	private static final FieldPosition HELPER_POSITION = new FieldPosition(0);
	
	private final static Format dateFormat = new SimpleDateFormat("MMddHHmmssS");

	private final static NumberFormat numberFormat = new DecimalFormat("0000");

	private static final String MAC_NAME = "HmacSHA1";
	public static final String ENCODING = "UTF-8";

	/**
	 * 获取密钥
	 * @return
	 */
	public static String getSecretKey(){
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < 32; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString(); 
	}
	
	/**
	 * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
	 * 
	 * @param encryptText
	 *            被签名的字符串
	 * @param encryptKey
	 *            密钥
	 * @return
	 * @throws Exception
	 */
	public static byte[] hmacSHA1Encrypt(String encryptText, String encryptKey)
			throws Exception {
		byte[] data = encryptKey.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);

		byte[] text = encryptText.getBytes(ENCODING);
		// 完成 Mac 操作
		return mac.doFinal(text);
	}
	
	/**
	 * 进行base64编码
	 * @param input
	 *            要编码的数据
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64(byte[] input) throws Exception {
		
		Class<?> clazz = Class
				.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod = clazz.getMethod("encode", byte[].class);
		mainMethod.setAccessible(true);
		Object retObj = mainMethod.invoke(null, new Object[] { input });
		return (String) retObj;
	}
	
	/**
	 * md5加密
	 * @param plainText
	 * @return
	 */
	public static String md5Encryption(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return re_md5;
	}
	
	/**
	 * 登录获取的token
	 * @return
	 */
	public static String getToken() {

		Calendar rightNow = Calendar.getInstance();

		StringBuffer sb = new StringBuffer();
		numberFormat.format(getRandomByCount(4), sb, HELPER_POSITION);
		
		dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);

		numberFormat.format(getRandomByCount(9), sb, HELPER_POSITION);
		

		return sb.toString();
	}
	
	/**
	 * 根据数量生产随机数
	 * @param count 要生成的随机个数
	 * @return
	 */
	public static int getRandomByCount(int count) {
		int bound = (int)Math.pow(10, count);
		int least = (bound) / 10;
		
		return ThreadLocalRandom.current().nextInt(least, bound);
	}

	/**
	 * 获取0到指定数值减一的随机数
	 * @param count
	 * @return
	 */
	public static Integer getNumByZeroToCount(int count) {
		return ThreadLocalRandom.current().nextInt(0, count);
	}
	

	/** 
	 * 计算地球上任意两点(经纬度)距离 
	 *  
	 * @param long1 
	 *            第一点经度 
	 * @param lat1 
	 *            第一点纬度 
	 * @param long2 
	 *            第二点经度 
	 * @param lat2 
	 *            第二点纬度 
	 * @return 返回距离 单位：米 
	 */  
	public static double getDistance(double long1, double lat1, double long2,
	        double lat2) {  
	    double a, b, R;  
	    R = 6378137; // 地球半径  
	    lat1 = lat1 * Math.PI / 180.0;  
	    lat2 = lat2 * Math.PI / 180.0;  
	    a = lat1 - lat2;  
	    b = (long1 - long2) * Math.PI / 180.0;  
	    double d;  
	    double sa2, sb2;  
	    sa2 = Math.sin(a / 2.0);  
	    sb2 = Math.sin(b / 2.0);  
	    d = 2  
	            * R  
	            * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
	                    * Math.cos(lat2) * sb2 * sb2));  
	    return d;  
	} 
    public static String plzh(String s, List<Integer> iL, List<String> is ,int m) {
        if(m == 0) {
            //System.out.println(s);
            //total++;
			if(s.length()>4){
				s = s.substring(0, s.length()-4);
			}
			sb.append(s+" OR");
			return null;
        }
        List<Integer> iL2;
        for(int i = 0; i < is.size(); i++) {
        	if(is.size()>1){
        		iL2 = new ArrayList<Integer>();
        		iL2.addAll(iL);
        		if(!iL.contains(i)) {
        			String str = s +" sgiid = "+ is.get(i)+" AND";
        			iL2.add(i);
        			plzh(str, iL2, is,m-1);
        		}
        	}
        }
        is.remove(0);
        return sb.toString();
    }
    private static StringBuilder sb = new StringBuilder(); 
}
