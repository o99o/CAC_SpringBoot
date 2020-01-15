package com.zit.cac.entity;
/**
 *@author: o99o
 *@date: 2015-5-18上午10:15:10
 *@version:
 *@description：easyui翻页时需要
 */
public class BaseEntity {
	/**
	 *  页码
	 */
	private Integer page;
	/**
	 *  每页大小
	 */
	private Integer rows;  
	/**
	 *  开始时间（如果有查询）
	 */
	private String start; 
	/**
	 *  结束时间（如果有查询）
	 */
	private String end;      
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
