package com.zit.cac.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zit.cac.dao.UserDao;
import com.zit.cac.service.UserService;

/**
 *@author: wangq
 *@date: 2015-5-18上午10:53:16
 *@version:
 *@description：
 */
@Service("userService")
public class UserServiceImpl<T> implements UserService<T>{
	
	@Autowired
	private UserDao<T> dao;
	
	@Override
	public List findUser(T t) throws Exception {
		return dao.findUser(t);
	}
	
	@Override
	public int countUser(T t) throws Exception {
		return dao.countUser(t);
	}
	
	@Override
	public T findOneUser(Long id) throws Exception {
		return dao.findOneUser(id);
	}
	
	@Override
	@Transactional
	public void addUser(T t) throws Exception {
		dao.addUser(t);
	}
	
	@Override
	@Transactional
	public void updateUser(T t) throws Exception {
		dao.updateUser(t);
	}
	
	@Override
	@Transactional
	public void deleteUser(Long id) throws Exception {
		dao.deleteUser(id);
	}
	
	@Override
	public T loginUser(Map map) throws Exception {
		return (T) dao.loginUser(map);
	}
	
	@Override
	public T existUserWithUserName(String userName) throws Exception {
		return dao.existUserWithUserName(userName);
	}
	
	@Override
	public T existUserWithRoleId(Long roleId) throws Exception {
		return dao.existUserWithRoleId(roleId);
	}
	@Override
	public T findOneUserAndRole(Long id) throws Exception {
		return dao.findOneUserAndRole(id);
	}
	
}
