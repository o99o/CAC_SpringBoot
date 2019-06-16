package com.zit.cac.service;

import java.util.List;

/**
 *@author: wangq
 *@date: 2015-8-11上午10:05:29
 *@version:
 *@description：
 */
public interface AttachmentService<T> {
	
	/**
	 * 新增附件
	 * @param t
	 * @throws Exception
	 */
	void insertAttachment(T t) throws Exception;
	
	/**
	 * 查询附件
	 * @param t
	 * @return
	 * @throws Exception
	 */
	List<T> findAttachment(T t) throws Exception;
	
	/**
	 * 附件计数
	 * @param t
	 * @return
	 * @throws Exception
	 */
	int countAttachment(T t) throws Exception;
	
}
