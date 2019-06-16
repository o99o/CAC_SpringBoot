package com.zit.cac.service;

import java.util.Map;

/**
 *@author: wangq
 *@date: 2015-8-15上午09:03:39
 *@version:
 *@description：
 */
public interface TokenService<T> {

	/**
	 * 新增token
	 * @param t
	 */
	void insertToken(T t);
	
	/**
	 * 查询token
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  T findOneToken(Map map);
	
}
