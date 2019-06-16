package com.zit.cac.service;

import java.util.List;

/**
 *@author: wangq
 *@date: 2015-8-7上午09:01:02
 *@version:
 *@description：
 */
public interface OperationService<T> {

	/**
	 * 查询操作
	 * @param t
	 * @return
	 * @throws Exception
	 */
	List<T> findOperation(T t) throws Exception;
	
	/**
	 * 操作计数
	 * @param t
	 * @return
	 * @throws Exception
	 */
	int countOperation(T t) throws Exception;
	
	/**
	 * 新增操作
	 * @param t
	 * @throws Exception
	 */
	void addOperation(T t) throws Exception;
	
	/**
	 * 修改操作
	 * @param t
	 * @throws Exception
	 */
	void updateOperation(T t) throws Exception;
	
	/**
	 * 删除操作
	 * @param operationId
	 * @throws Exception
	 */
	void deleteOperation(Long operationId) throws Exception;
	
	/**
	 * 查询操作
	 * @param operationId
	 * @return
	 * @throws Exception
	 */
	T findOperationById(Long operationId) throws Exception;
	
}