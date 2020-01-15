package com.zit.cac.entity;

import java.io.Serializable;

/**
 * @author: o99o
 * @date: 2015-5-18上午10:14:58
 * @version: @description：
 */
public class User extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户类型
	 */
	private Integer userType;
	/**
	 * 所属角色ID
	 */
	private Long roleId;
	/**
	 * 用户描述
	 */
	private String userDescription;
	/**
	 * 所属角色名称
	 */
	private String roleName;
	/**
	 * 用户真实姓名
	 */
	private String userRealName;
	/**
	 * 用户电话
	 */
	private String userPhone;
	/**
	 * 用户邮箱
	 */
	private String userEmail;
	/**
	 * 用户住址
	 */
	private String userAddress;
	/**
	 * 用户单位
	 */
	private String userDepartment;

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

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserDepartment() {
		return userDepartment;
	}

	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", userType=" + userType
				+ ", roleId=" + roleId + ", userDescription=" + userDescription + ", roleName=" + roleName
				+ ", userRealName=" + userRealName + ", userPhone=" + userPhone + ", userEmail=" + userEmail
				+ ", userAddress=" + userAddress + ", userDepartment=" + userDepartment + "]";
	}

}
