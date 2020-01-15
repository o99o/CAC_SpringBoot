package com.zit.cac.entity;
/**
 *@author: o99o
 *@date: 2015-8-15上午08:57:46
 *@version:
 *@description：
 */
public class Token {
	/**
	 *  自增ID
	 */
	private Long tokenId;      
	/**
	 *  用户ID
	 */
	private Long userId;  
	/**
	 *  随机数(md5形式 UUID)
	 */
	private String userAgent;   
	/**
	 *  TOKEN值(md5(username+md5(useragent))自己定义的加密方式)
	 */
	private String token;       
	/**
	 *  创建时间
	 */
	private String createTime;      
	/**
	 *  过期时间
	 */
	private String expireTime;        
	
	public Long getTokenId() {
		return tokenId;
	}
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	@Override
	public String toString() {
		return "Token [tokenId=" + tokenId + ", userId=" + userId
				+ ", userAgent=" + userAgent + ", token=" + token
				+ ", createTime=" + createTime + ", expireTime=" + expireTime
				+ "]";
	}
	
	
}