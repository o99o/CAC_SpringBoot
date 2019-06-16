package com.zit.cac.dao;

import java.util.List;
import java.util.Map;


/**
 *@author: wangq
 *@date: 2015-5-18上午10:30:05
 *@version:
 *@description：
 */
public interface UserDao<T> {

	/**
	 *  查询所有
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public  List<T> findUser(T t) throws Exception;
	
	/**
	 *  数量
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public  int countUser(T t) throws Exception;
	
	/**
	 *  通过ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public  T findOneUser(Long id) throws Exception;
	
	/**
	 *  新增
	 * @param t
	 * @throws Exception
	 */
	public  void addUser(T t) throws Exception;
	
	/**
	 *  修改
	 * @param t
	 * @throws Exception
	 */
	public  void updateUser(T t) throws Exception;
	
	/**
	 *  删除
	 * @param id
	 * @throws Exception
	 */
	public  void deleteUser(Long id) throws Exception;
	
	/**
	 *  登录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public  T loginUser(Map<String, String> map) throws Exception;
	
	/**
	 * 通过用户名判断是否存在，（新增时不能重名）
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public  T existUserWithUserName(String userName) throws Exception;
	
	/**
	 *  通过角色判断是否存在
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public  T existUserWithRoleId(Long roleId) throws Exception;
	
	/**
	 * 根据用户id查询用户信息包含用户角色字段
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public  T findOneUserAndRole(Long id) throws Exception;
	
}
