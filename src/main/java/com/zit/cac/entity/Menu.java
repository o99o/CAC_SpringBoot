package com.zit.cac.entity;

import java.io.Serializable;

/**
 *@author: wangq
 *@date: 2015-5-18下午04:19:15
 *@version:
 *@description：
 */
public class Menu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  菜单ID
	 */
	private Long menuId;       
	/**
	 *  菜单名称
	 */
	private String menuName;    
	/**
	 *  菜单地址
	 */
	private String menuUrl;    
	/**
	 *  上级菜单ID
	 */
	private Long parentId;      
	/**
	 *  菜单描述
	 */
	private String menuDescription; 
	/**
	 *  状态
	 */
	private String state;  
	/**
	 *  图标
	 */
	private String iconCls;    
	/**
	 *  顺序
	 */
	private Integer seq;   
	/**
	 *  菜单ID数组
	 */
	private Long[] menuIds;      
	
	/**
	 * 操作按钮名称合集
	 */
	private String operations;
	
	/**
	 * 菜单级别：-1-顶级；1-一级；2-二级；
	 */
	private int level;
	
	
	
	
	public String getOperations() {
		return operations;
	}
	public void setOperations(String operations) {
		this.operations = operations;
	}
	public Long[] getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(Long[] menuIds) {
		this.menuIds = menuIds;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getMenuDescription() {
		return menuDescription;
	}
	public void setMenuDescription(String menuDescription) {
		this.menuDescription = menuDescription;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
}
