package com.zit.cac.service;

import java.util.List;
import java.util.Map;

import com.zit.cac.entity.Menu;


/**
 *@author: wangq
 *@date: 2015-5-18下午04:32:47
 *@version:
 *@description：
 */
public interface MenuService<T> {

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
	 * 新增菜单
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
	 * 查询菜单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	List<T> menuTree(Map map) throws Exception;
	/**
	 * 根据menu的id查询menu对象
	 * @param menuId
	 * @return
	 * @throws Exception
	 */
	T findMenuById(Long menuId) throws Exception;
}
