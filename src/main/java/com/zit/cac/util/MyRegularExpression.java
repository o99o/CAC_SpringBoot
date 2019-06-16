package com.zit.cac.util;

import java.util.regex.Pattern;

/**
 * 
* <p>Title: MyRegularExpression</p>
* <p>Description: </p>
* <p>Company: ZIT</p>
* @author LiJiangtao
* @date   2018年6月12日
 */
public class MyRegularExpression {
	
	/**
	 * 去掉空字符
	 */
	public static Pattern blankPattern = Pattern.compile("\\s*|\t|\r|\n");
}
