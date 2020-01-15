package com.zit.cac.entity;

import java.io.Serializable;

/**
 *@author: o99o
 *@date: 2015-8-6下午05:17:43
 *@version: 
 *@description：操作按钮所拥有的属性
 */
public class Operation extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 按钮ID
	 */
	private Long operationId; 
	/**
	 * 所属哪一个页面菜单的ID
	 */
	private Long menuId;       
	/**
	 * 按钮名称
	 */
	private String operationName;  
	private String menuName;
	
	
	
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Long getOperationId() {
		return operationId;
	}
	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
}
