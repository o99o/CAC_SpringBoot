package com.zit.cac.util;

import java.io.InputStream;
import java.util.Properties;

/**
 *@author: o99o
 *@date: 2015-8-14下午01:51:16
 *@version:
 *@description：
 */
public class PropertiesUtil {
	
	public static String url;
	
	static {
		Properties pros = new Properties();
		InputStream in = PropertiesUtil.class.getResourceAsStream("/log4j.properties");
		try {
			pros.load(in);
			url= pros.getProperty("log4j.appender.logfile.File");
		} catch (Exception e) {
			System.out.println("配置文件加载失败");
			e.printStackTrace();
		}
	}
	
	
}

