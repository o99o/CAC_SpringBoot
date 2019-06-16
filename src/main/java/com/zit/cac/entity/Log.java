package com.zit.cac.entity;
/**
 *@author: wangq
 *@date: 2015-8-1下午03:18:56
 *@version:
 *@description： 操作日志
 */
public class Log extends BaseEntity{
	/**
	 * 主键自增长ID 
	 */
	private Long logId;       
	/**
	 * 操作人
	 */
	private String userName;       
	/**
	 * 时间
	 */
	private String createTime;    
	/**
	 * 详细内容
	 */
	private String content;     
	/**
	 * 操作类型（增删改）
	 */
	private String operation;      
	/**
	 * IP
	 */
	private String ip;   
	/**
	 * 所属模块
	 */
	private String module;               
	
	
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	
	
}
