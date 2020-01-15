package com.zit.cac.dao;

import java.util.List;


/**
 *@author: o99o
 *@date: 2015-8-11上午10:04:14
 *@version:
 *@description：
 */
public interface AttachmentDao<T> {

	/**
	 * 插入
	 * @param t
	 * @throws Exception
	 */
	void insertAttachment(T t) throws Exception; 
	
	/**
	 * 查询
	 * @param t
	 * @return
	 * @throws Exception
	 */
	List<T> findAttachment(T t) throws Exception;
	
	/**
	 * 统计
	 * @param t
	 * @return
	 * @throws Exception
	 */
	int countAttachment(T t) throws Exception;
	
}
