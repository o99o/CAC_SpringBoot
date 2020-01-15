package com.zit.cac.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zit.cac.entity.Menu;
import com.zit.cac.entity.Operation;
import com.zit.cac.service.MenuService;
import com.zit.cac.service.OperationService;
import com.zit.cac.util.Jackson2Util;
import com.zit.cac.util.StringUtil;

/**
 *@author: o99o
 *@date: 2015-8-7下午04:06:10
 *@version:
 *@description：
 */
@Controller
@RequestMapping("cacOperation")
public class CacOperationController {
	
	private Operation operation;
	@Autowired
	private MenuService<Menu> menuService;
	@Autowired
	private OperationService<Operation> operationService;
	private int rows = 10;
	private int page = 1;
	static Logger logger = LogManager.getLogger(CacOperationController.class);
	
	@RequestMapping("operationList")
	public ModelAndView list(Integer curr,Integer limit,Long menuId){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			page = curr==null?1:curr;
			rows = limit==null?10:limit;
			operation = new Operation();
			operation.setMenuId(menuId);
			
			// 使用PageHelper分页
			PageHelper.startPage(page, rows);
			List<Operation> list = operationService.findOperation(operation);
			Page<Operation> page = (Page<Operation>)list;
			long total=page.getTotal();
			
			mv.addObject("count",total );
			mv.addObject("code", 0);
			mv.addObject("data", list);
	        
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("按钮展示错误",e);
		}
		
		return mv;
	}
	
	
	
	
	@RequestMapping("reserveOperation")
	public ModelAndView reserveMenu(Operation operation){
		String operationId = operation.getOperationId()+"";
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			//更新操作
			if (StringUtil.isNotEmpty(operationId)) {
				operationService.updateOperation(operation);
			} else {
				operationService.addOperation(operation);
			}
			mv.addObject("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("按钮保存错误",e);
			mv.addObject("errorMsg", "对不起，操作失败！");
		}
		return mv;
	}
	
	@RequestMapping("deleteOperation")
	public ModelAndView delUser(String idsStr){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			String[] ids=idsStr.split(",");
			for (String id : ids) {
				operationService.deleteOperation(Long.parseLong(id));
			}
			mv.addObject("success", true);
			mv.addObject("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除按钮错误",e);
			mv.addObject("errorMsg", "对不起，删除失败");
		}
		return mv;
	}
	/**
	 * 按钮管理的弹层
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("buttonManagePop/{id}")
	public ModelAndView operationPop(@PathVariable Long id,HttpSession session){
		Menu menu = null;
		try {
			menu = menuService.findMenuById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		session.setAttribute("menu", menu);
		session.setAttribute("menuId", id);
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/pop/operation_manage_pop");
		return mv;
	}
	/**
	 * 修改添加iCon的弹层 
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("buttonEditPop/{id}")
	public ModelAndView buttonEditPop(@PathVariable Long id,HttpSession session){
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/pop/operation_manage_pop");
		
		Operation operation =null;
		try {
			if(null!=id) {
				operation = operationService.findOperationById(id);
				String operationObjStr = JSON.toJSONString(operation);
				session.setAttribute("operation", operationObjStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加或修改按钮错误",e);
		}
		
		return mv;
	}
	
	@RequestMapping("buttonAddPop/{menuId}")
	public ModelAndView buttonAddPop(@PathVariable Long menuId,HttpSession session){
		try {
			Menu menuObjStr =  menuService.findMenuById(menuId);
			session.setAttribute("menuForOperate", menuObjStr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加或修改按钮错误",e);
		}
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/pop/button_pop_add");
		return mv;
	}
	
	
}
