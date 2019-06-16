package com.zit.cac.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *@author: wangq
 *@date: 2015-8-4上午09:52:14
 *@version:
 *@description：
 */
public class TimeUtil {
	
	/**
	 * 将日期格式化成String类型
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatTime(Date date,String pattern){
		return new SimpleDateFormat(pattern).format(date);
	}
}
