package com.zit.cac.entity;

import java.io.Serializable;

/**
 *@author: wangq
 *@date: 2015-5-18下午04:51:51
 *@version:
 *@description：
 */
public class Role extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	private Long roleId;      
	/**
	 * 角色名称
	 */
	private String  roleName;  
	/**
	 * 菜单ID集合，用,分开的
	 */
	private String  menuIds;    
	/**
	 * 按钮ID集合，用,分开的
	 */
	private String  operationIds;    
	/**
	 * 角色描述
	 */
	private String  roleDescription;        
	
	
	
	public String getOperationIds() {
		return operationIds;
	}
	public void setOperationIds(String operationIds) {
		this.operationIds = operationIds;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	
}
