package com.zit.cac.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zit.cac.dao.MenuDao;
import com.zit.cac.entity.Menu;
import com.zit.cac.service.MenuService;

/**
 *@author: wangq
 *@date: 2015-5-18下午04:33:08
 *@version:
 *@description：
 */
@Service("menuService")
public class MenuServiceImpl<T> implements MenuService<T>{

	@Autowired
	private MenuDao<T> dao;
	
	@Override
	@Transactional
	public void addMenu(T t) throws Exception {
		dao.addMenu(t);
	}

	@Override
	public int countMenu(T t) throws Exception {
		return dao.countMenu(t);
	}

	@Override
	@Transactional
	public void deleteMenu(Long menuId) throws Exception {
		dao.deleteMenu(menuId);
	}

	@Override
	public List<T> findMenu(T t) throws Exception {
		return dao.findMenu(t);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> menuTree(Map map) throws Exception {
		return dao.menuTree(map);
	}

	@Override
	@Transactional
	public void updateMenu(T t) throws Exception {
		dao.updateMenu(t);
	}

	@Override
	public T findMenuById(Long menuId) throws Exception {
		return dao.findOneMenu(menuId);
	}
}
