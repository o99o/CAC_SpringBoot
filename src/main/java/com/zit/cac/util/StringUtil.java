package com.zit.cac.util;



/**
 *@author: o99o
 *@date: 2015-5-18上午10:59:25
 *@version:
 *@description：
 */
public class StringUtil {
	
	/**
	 * 判断字符串是空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if("".equals(str)|| str==null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断字符串不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		//2018-05-31  lihongjie 做出了修改 
		//新增不等于“null”判断 ,新添加代码为：&&(!"null".equals(str))
		if((!"".equals(str))&&(str!=null)&&(!MagicValue.NULL.getDesc().equals(str.toLowerCase()))){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 判断某一个字符串数组中是否含有某一字符串
	 * @param str
	 * @param strArr
	 * @return
	 */
	public static boolean existStrArr(String str,String []strArr){
		for(int i=0;i<strArr.length;i++){
			if(strArr[i].equals(str)){
				return true;
			}
		}
		return false;
	}
	
	
	
}
