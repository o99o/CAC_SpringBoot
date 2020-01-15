package com.zit.cac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zit.cac.dao.LogDao;
import com.zit.cac.service.LogService;

/**
 *@author: o99o
 *@date: 2015-8-1下午03:22:48
 *@version:
 *@description：
 */
@Service("logService")
public class LogServiceImpl<T> implements LogService<T>{

	@Autowired
	private LogDao<T> dao ;
	
	@Override
	public int countLog(T t) throws Exception {
		return dao.countLog(t);
	}
	@Override
	public List<T> findLog(T t) throws Exception {
		return dao.findLog(t);
	}
	
	@Override
	@Transactional
	public void insertLog(T t) throws Exception {
		dao.insertLog(t);
	}

	@Override
	@Transactional
	public void deleteLog(Long logId) throws Exception {
		dao.deleteLog(logId);
	}

	@Override
	@Transactional
	public void truncateLog() throws Exception {
		dao.truncateLog();
	}
	
	

}
