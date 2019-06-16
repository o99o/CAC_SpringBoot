package com.zit.cac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zit.cac.dao.OperationDao;
import com.zit.cac.service.OperationService;

/**
 *@author: wangq
 *@date: 2015-8-7上午09:01:44
 *@version:
 *@description：
 */
@Service("operationService")
public class OperationServiceImpl<T> implements OperationService<T>{
	
	@Autowired
	private OperationDao<T> dao;
	

	@Override
	@Transactional
	public void addOperation(T t) throws Exception {
		dao.addOperation(t);
	}

	@Override
	public int countOperation(T t) throws Exception {
		return dao.countOperation(t);
	}

	@Override
	@Transactional
	public void deleteOperation(Long operationId) throws Exception {
		dao.deleteOperation(operationId);
	}

	@Override
	public List<T> findOperation(T t) throws Exception {
		return dao.findOperation(t);
	}

	@Override
	@Transactional
	public void updateOperation(T t) throws Exception {
		dao.updateOperation(t);
	}

	@Override
	public T findOperationById(Long operationId) throws Exception {
		return dao.findOperationById(operationId);
	}

}
