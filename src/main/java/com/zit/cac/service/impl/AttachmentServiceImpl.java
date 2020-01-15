package com.zit.cac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zit.cac.dao.AttachmentDao;
import com.zit.cac.service.AttachmentService;

/**
 *@author: o99o
 *@date: 2015-8-11上午10:06:31
 *@version:
 *@description：
 */
@Service("attachmentService")
public class AttachmentServiceImpl<T> implements AttachmentService<T>{

	@Autowired
	private AttachmentDao<T> dao;
	
	@Override
	@Transactional
	public void insertAttachment(T t) throws Exception {
		dao.insertAttachment(t);
	}
	
	@Override
	public int countAttachment(T t) throws Exception {
		return dao.countAttachment(t);
	}

	@Override
	public List<T> findAttachment(T t) throws Exception {
		return dao.findAttachment(t);
	}

}
