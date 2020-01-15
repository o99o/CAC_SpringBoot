package com.zit.cac.entity;

import java.io.Serializable;
/**
 *@author: o99o
 *@date: 2015-5-18上午10:14:58
 *@version:
 *@description：
 */
public class UserRole extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  用户ID
	 */
	private Long userId;   
	/**
	 *  用户名
	 */
	private String userName; 
	/**
	 *  密码
	 */
	private String password;   
	/**
	 *  用户类型
	 */
	private Integer userType;  
	/**
	 *  所属角色ID
	 */
	private Long roleId;    
	/**
	 *  用户描述
	 */
	private String userDescription;  
	/**
	 *  所属角色名称
	 */
	private String roleName;         


	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getUserDescription() {
		return userDescription;
	}
	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", password=" + password + ", userType=" + userType
				+ ", roleId=" + roleId + ", userDescription=" + userDescription
				+ ", roleName=" + roleName + "]";
	}
	
	
	
}
