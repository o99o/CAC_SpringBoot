package com.zit.cac.dao;

import java.util.Map;

/**
 *@author: o99o
 *@date: 2015-8-15上午09:02:53
 *@version:
 *@description：
 */
public interface TokenDao<T> {
	
	/**
	 * 新增角色
	 * @param t
	 */
	void insertToken(T t);
	
	/**
	 * 查询角色
	 * @param map
	 * @return
	 */
	T findOneToken(Map map);
	
}
