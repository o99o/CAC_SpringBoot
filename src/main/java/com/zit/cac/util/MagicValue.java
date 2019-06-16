package com.zit.cac.util;

import org.springframework.core.DecoratingClassLoader;

/**
 * 
* <p>Title: MagicValue</p>
* <p>Description: </p>
* <p>Company: ZIT</p>
* @author LiJiangtao
* @date   2018年6月12日
 */
public enum MagicValue {
	/**
	 * 登录验证码
	 */
	IMAGE_CODE("sRand"),
	/**
	 * 当前用户
	 */
	CURRENT_USER("currentUser"),
	/**
	 * 登录
	 */
	LOGIN("login"),
	/**
	 * ip未知
	 */
	UNKNOWN("unknown"),
	/**
	 * null
	 */
	NULL("null"),
	/**
	 * 当请求的路径中包含webservice字符串时，不进行session验证
	 */
	WEBSERVICE("webservice");
	;
	
	private String desc;
	private MagicValue(String desc) {
		this.desc=desc;
	}
	
	public String getDesc() {
		return desc;
	}
	
}
