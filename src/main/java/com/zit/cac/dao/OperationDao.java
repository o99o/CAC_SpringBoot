package com.zit.cac.dao;

import java.util.List;


/**
 *@author: wangq
 *@date: 2015-8-6下午05:23:42
 *@version:
 *@description：
 */
public interface OperationDao<T> {

	/**
	 * 查询操作
	 * @param t
	 * @return
	 * @throws Exception
	 */
	List<T> findOperation(T t) throws Exception;
	
	/**
	 * 权限计数
	 * @param t
	 * @return
	 * @throws Exception
	 */
	int countOperation(T t) throws Exception;
	
	/**
	 * 添加操作
	 * @param t
	 * @throws Exception
	 */
	void addOperation(T t) throws Exception;
	
	/**
	 * 操作记录的插入方法，因为在aop中拦截的是以add开头的方法，但是此操作不需要拦截，所以用insert开头名字的方法
	 * @param t
	 * @throws Exception
	 */
	void updateOperation(T t) throws Exception;
	
	/**
	 * 删除操作
	 * @param operationId
	 * @throws Exception
	 */
	public  void deleteOperation(Long operationId) throws Exception;
	
	/**
	 * 通过id获得操作
	 * @param operationId
	 * @return
	 * @throws Exception
	 */
	public  T findOperationById(Long operationId) throws Exception;
	
}
