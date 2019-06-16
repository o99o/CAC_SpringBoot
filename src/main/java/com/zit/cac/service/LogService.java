package com.zit.cac.service;

import java.util.List;


/**
 *@author: wangq
 *@date: 2015-8-1下午03:22:25
 *@version:
 *@description：
 */
public interface LogService<T> {

	/**
	 * 新增日志
	 * @param t
	 * @throws Exception
	 */
	void insertLog(T t) throws Exception;
	
	/**
	 * 查询日志
	 * @param t
	 * @return
	 * @throws Exception
	 */
	List<T> findLog(T t) throws Exception;
	
	/**
	 * 日志计数
	 * @param t
	 * @return
	 * @throws Exception
	 */
	int countLog(T t) throws Exception;
	
	/**
	 * 删除日志
	 * @param logId
	 * @throws Exception
	 */
	public  void deleteLog(Long logId) throws Exception;
	
	/**
	 * 删除表
	 * @throws Exception
	 */
	public  void truncateLog() throws Exception;
}
