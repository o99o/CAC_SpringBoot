package com.zit.cac.dao;

import java.util.List;
import java.util.Map;


/**
 *@author: wangq
 *@date: 2015-5-18下午04:31:26
 *@version:
 *@description：
 */
public interface MenuDao<T> {
	
	/**
	 * 查询菜单
	 * @param t
	 * @return
	 * @throws Exception
	 */
	 List<T> findMenu(T t) throws Exception;
	
	/**
	 * 菜单计数
	 * @param t
	 * @return
	 * @throws Exception
	 */
	int countMenu(T t) throws Exception;
	
	/**
	 * 添加菜单
	 * @param t
	 * @throws Exception
	 */
	void addMenu(T t) throws Exception;
	
	/**
	 * 修改菜单
	 * @param t
	 * @throws Exception
	 */
	void updateMenu(T t) throws Exception;
	
	/**
	 * 删除菜单
	 * @param menuId
	 * @throws Exception
	 */
	void deleteMenu(Long menuId) throws Exception;
	
	/**
	 * 获取菜单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<T> menuTree(Map map) throws Exception;
	
	/**
	 * 查找menu
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	T findOneMenu(Long menuId) throws Exception;
	
}
