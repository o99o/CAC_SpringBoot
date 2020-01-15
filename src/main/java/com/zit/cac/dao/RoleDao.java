package com.zit.cac.dao;

import java.util.List;
import java.util.Map;


/**
 *@author: o99o
 *@date: 2015-5-18下午04:53:34
 *@version:
 *@description：
 */
public interface RoleDao<T> {

	/**
	 * 查询角色
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	T findOneRole(Long roleId) throws Exception;
	
	/**
	 * 查找角色
	 * @param t
	 * @return
	 * @throws Exception
	 */
	List<T> findRole(T t) throws Exception;
	
	/**
	 * 角色计数
	 * @param t
	 * @return
	 * @throws Exception
	 */
	int countRole(T t) throws Exception;
	
	/**
	 * 删除角色
	 * @param roleId
	 * @throws Exception
	 */
	void deleteRole(Long roleId) throws Exception;
	
	/**
	 * 新增角色
	 * @param t
	 * @throws Exception
	 */
	void addRole(T t) throws Exception;
	
	/**
	 * 修改角色
	 * @param t
	 * @throws Exception
	 */
	void updateRole(T t) throws Exception;
	
	/**
	 * 通过名称判断是否存在，（新增时不能重名）
	 * @param roleName
	 * @return
	 * @throws Exception
	 */
	T existRoleWithRoleName(String roleName) throws Exception;
	
	/**
	 * 批量删除
	 * @param map
	 * @throws Exception
	 */
	void deleteRoleByRoleIds(Map map) throws Exception;
}
