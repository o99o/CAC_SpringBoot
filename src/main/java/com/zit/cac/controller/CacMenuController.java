package com.zit.cac.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zit.cac.entity.Menu;
import com.zit.cac.entity.Operation;
import com.zit.cac.service.MenuService;
import com.zit.cac.service.OperationService;
import com.zit.cac.util.Jackson2Util;
import com.zit.cac.util.StringUtil;
/**
 * 
 * @author LiHongjie
 *
 * 2018年6月5日
 */
@RequestMapping("cacMenu")
@Controller
public class CacMenuController {

	private Menu menu;
	private Operation operation;
	@Autowired
	private MenuService<Menu> menuService;
	@Autowired
	private OperationService<Operation> operationService;
	
	static Logger logger = LogManager.getLogger(CacMenuController.class);
	
	
	@RequestMapping("cacMenuIndex")
	public ModelAndView indexMenu(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/cac_menu");
		return mv;
	}
	
	@RequestMapping("treeGridMenu")
	public ModelAndView treeGridMenu(String parentId){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			JSONArray jsonArray = getListByParentId(parentId);
			mv.addObject(jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单展示错误",e);
		}
		
		return mv;
	}
	
	
	
	
	private JSONArray getListByParentId(String parentId)throws Exception{
		JSONArray jsonArray=this.getTreeGridMenuByParentId(parentId);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if("open".equals(jsonObject.getString("state"))){
				continue;
			}else{
				jsonObject.put("children", getListByParentId(jsonObject.getString("id")));
			}
		}
		return jsonArray;
	}
	
	
	
	private JSONArray getTreeGridMenuByParentId(String parentId)throws Exception{
		JSONArray jsonArray=new JSONArray();
		menu = new Menu();
		menu.setParentId(Long.parseLong(parentId));
		List<Menu> list = menuService.findMenu(menu);
		for(Menu menu : list){
			JSONObject jsonObject = new JSONObject();
			Long menuId = menu.getMenuId();
			jsonObject.put("id", menuId);
			jsonObject.put("name", menu.getMenuName());
			jsonObject.put("spread", menu.getState());
			
			// 加上该页面菜单下面的按钮
			operation = new Operation();
			operation.setMenuId(menuId);
			List<Operation> operaList = operationService.findOperation(operation);
			if (operaList!=null && operaList.size()>0) {
				String string = "";
				for (Operation o : operaList) {
					string += o.getOperationName() + ",";
				}
				jsonObject.put("operationNames", string.substring(0,string.length()-1));
			} else {
				jsonObject.put("operationNames", "");
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	
	
	@RequestMapping("reserveMenu")
	public ModelAndView reserveMenu(Menu menu){
		Long menuId = menu.getMenuId();
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			//更新操作
			if (null!=menuId) {  
				menu.setMenuId(menuId);
				menuService.updateMenu(menu);
				
				mv.addObject("success", true);
				mv.addObject("msg", "修改成功！");
			} else {
				Long parentId = menu.getParentId();
				menu.setParentId(parentId);
				if (isLeaf(parentId+"")) {
					// 添加操作
					menuService.addMenu(menu);  
					// 更新他上级状态。变成CLOSED
					menu = new Menu();
					menu.setMenuId(parentId);
					menu.setState("closed");
					menuService.updateMenu(menu);
				} else {
					// 判断是否存在同名子菜单
					PageHelper.startPage(1, 1);
					List<Menu> list = menuService.findMenu(menu);
					if(list.size()>0) {
						mv.addObject("success", false);
						mv.addObject("msg", "同一菜单下，不允许同名子菜单");
						return mv;
					}else {
						menuService.addMenu(menu);
					}
				}
				
				mv.addObject("success", true);
				mv.addObject("msg", "新增成功！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单保存错误",e);
			mv.addObject("success", false);
			mv.addObject("msg", "对不起，操作失败！");
		}
		
		return mv;
	}
	
	
	/**
	 * 判断是不是叶子节点
	 * @param menuId
	 * @return
	 */
	public boolean isLeaf(String menuId){
		boolean flag = false;
		try {
			menu = new Menu();
			if(StringUtil.isNotEmpty(menuId)) {
				menu.setParentId(Long.parseLong(menuId));
				List<Menu> list = menuService.findMenu(menu);
				if (list==null || list.size()==0) {
					flag = true;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("判断菜单是否叶子节点错误",e);
		}
		return flag;
	}
	
	
	
	
	@RequestMapping("deleteMenu")
	public ModelAndView deleteMenu(String deleteMenuId){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		Menu menu = null;
		try {
			String menuId = deleteMenuId;
			if(StringUtil.isNotEmpty(menuId)) {
				menu = menuService.findMenuById(Long.parseLong(menuId));
			}
			Long parentId = menu.getParentId();
			
			/// 菜单下面存在Operation则不能删除
			Operation operation=new Operation();
			operation.setMenuId(Long.parseLong(menuId));
			// 使用PageHelper分页
			PageHelper.startPage(1, 1);
			List<Operation> operationList = operationService.findOperation(operation);
			if(operationList.size()>0){
				//不是叶子节点，说明有子菜单，不能删除
				mv.addObject("success", false);
				mv.addObject("msg", "该菜单包含按钮，不能直接删除");
			}else if (!isLeaf(menuId)) {  
				mv.addObject("success", false);
				mv.addObject("msg", "该菜单下面有子菜单，不能直接删除");
			} else {
				menu = new Menu();
				menu.setParentId(parentId);
				int sonNum = menuService.countMenu(menu);
				if (sonNum == 1) {  
					// 只有一个孩子，删除该孩子，且把父亲状态置为open
					menu = new Menu();
					menu.setMenuId(parentId);
					menu.setState("open");
					menuService.updateMenu(menu);
					
					menuService.deleteMenu(Long.parseLong(menuId));
				} else {
					//不只一个孩子，直接删除
					menuService.deleteMenu(Long.parseLong(menuId));
				}
				mv.addObject("msg", "删除成功");
				mv.addObject("success", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("菜单删除错误",e);
			mv.addObject("success", false);
			mv.addObject("msg", "对不起，删除失败！");
		}
		
		return mv;
	}
	
	/**
	 * 返回菜单信息弹出层
	 * @return
	 */
	@RequestMapping("menuUpdatePop/{id}")
	public ModelAndView menuInfoPop(@PathVariable Long id, HttpSession httpSession){
		ModelAndView mv=new ModelAndView();
		try {
			mv.setViewName("zlits_cac/pop/menu_pop_update");
			Menu menu = menuService.findMenuById(id);
			
			int menuLevel=findMenuLevel(id);
			menu.setLevel(menuLevel);
			
			String menuJsonStr = JSON.toJSONString(menu);
			httpSession.setAttribute("data", menuJsonStr);
		} catch (Exception e) {
			logger.error("菜单查询错误",e);
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 返回添加菜单跳转的弹出层页面
	 * 包含parentId
	 * @return
	 */
	@RequestMapping("menuAddPop/{id}")
	public ModelAndView menuAddPop(@PathVariable Long id, HttpSession httpSession){
		ModelAndView mv=new ModelAndView();
		try {
			mv.setViewName("zlits_cac/pop/menu_pop_add");
			
			Menu menu = menuService.findMenuById(id);
			int menuLevel=findMenuLevel(id);
			menu.setLevel(menuLevel);
			
			String menuJsonStr = JSON.toJSONString(menu);
			httpSession.setAttribute("data", menuJsonStr);
		} catch (Exception e) {
			logger.error("菜单查询错误",e);
			e.printStackTrace();
		}
		return mv;
	}
	/**
	 * 按钮管理
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("buttonManagePop/{id}")
	public ModelAndView buttonManagePop(@PathVariable Long id, HttpSession httpSession){
		ModelAndView mv=new ModelAndView();
		try {
			mv.setViewName("zlits_cac/pop/menu_pop_add");
			
			Menu menu = menuService.findMenuById(id);
			String menuJsonStr = JSON.toJSONString(menu);
			httpSession.setAttribute("data", menuJsonStr);
		} catch (Exception e) {
			logger.error("菜单查询错误",e);
			e.printStackTrace();
		}
		return mv;
	}


	/**
	 * 判断当前菜单级别是否是二级菜单
	 * @param request
	 * @param response
	 * @param menuId
	 * @return
	 */
	@RequestMapping("judgeMenuLevel")
	@ResponseBody
	public ModelAndView judgeMenuLevel(Long menuId){
		int menuLevel=findMenuLevel(menuId);
		String level=menuLevel<2?"true":"false";
		
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		mv.addObject(level);
		return mv;
	}
	
	private int findMenuLevel(Long menuId) {
		int level=-1;
		
		//一级菜单父菜单id标识
		final long oneLenvelParentMenuId = -1;
		if(null!=menuId) {
			try {
				Menu menu = menuService.findMenuById(menuId);
				
				if(null != menu) {
					// 判断是否是系统顶层菜单
					if(oneLenvelParentMenuId == menu.getParentId()) {
						return -1;
					}
					
					//判断是否是系统的一级级菜单
					Menu parentMenu = menuService.findMenuById(menu.getParentId());
					if(null!=parentMenu && oneLenvelParentMenuId== parentMenu.getParentId()) {
						return 1;
					}
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				logger.error("转换菜单id数据类型发生错误",e);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("判断菜单级别发生错误",e);
			}
		}
		return 2;
	}
	
}
