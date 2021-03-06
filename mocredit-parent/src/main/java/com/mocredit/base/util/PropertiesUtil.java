package com.mocredit.base.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Properties工具类
 * 
 * @author lzl
 * @version 2012-9-11 下午01:10:22
 */
public class PropertiesUtil {
	private Properties properties;
	private static PropertiesUtil piu =new PropertiesUtil();


	public static String getValue(String key) {
		return piu.properties.getProperty(key, null);
	}
	
	/**
	 * 获取属性列表，根据传入参数的模糊匹配
	 * @param key,需要查找属性前缀
	 * @describe
	 * 		比如属性文件中有
	  			mail.smtp.host=smtp.incito.com.cn
				mail.smtp.port=25
				mail.smtp.auth=true
			通过该方法，便可以使用findValues("mail.smtp")将所有相关属性获取，并放入一个LinkedHashMap中
	 * @return
	 */
	public static Map<String,String> findValues(String key) {
		Map<String,String> propMap = new LinkedHashMap<String,String>();
		Iterator<Object> it = piu.properties.keySet().iterator();
		while(it.hasNext()){
			String k = String.valueOf(it.next());
			if(k.startsWith(key)){
				String v = piu.properties.getProperty(k, null);
				propMap.put(k, v);
			}
		}
		return propMap;
	}
	public static void  init(Properties prop){
		piu.properties=prop;
	}
}
