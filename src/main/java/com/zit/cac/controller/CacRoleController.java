package com.zit.cac.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zit.cac.entity.Menu;
import com.zit.cac.entity.Operation;
import com.zit.cac.entity.Role;
import com.zit.cac.entity.User;
import com.zit.cac.service.MenuService;
import com.zit.cac.service.OperationService;
import com.zit.cac.service.RoleService;
import com.zit.cac.service.UserService;
import com.zit.cac.util.Jackson2Util;
import com.zit.cac.util.MyRegularExpression;
import com.zit.cac.util.StringUtil;

/**
 * 
 * @author LiHongjie
 *
 * 2018年5月17日
 */
@Controller
@RequestMapping("cacRole")
public class CacRoleController {

	private int page;
	private int rows;
	private Role role;
	private Operation operation;
	@Autowired
	private UserService<User> userService;
	@Autowired
	private RoleService<Role> roleService;
	private Map map;
	private Menu menu;
	@Autowired
	private MenuService<Menu> menuService;
	@Autowired
	private OperationService<Operation> operationService;
	static Logger logger = LogManager.getLogger(CacRoleController.class);
	
	
	@RequestMapping("cacRoleIndex")
	public ModelAndView index(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/cac_role");
		return mv;
	}
	
	@RequestMapping("cacRoleList")
	public ModelAndView roleList(Integer curr,Integer limit,String roleName){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			page = curr;
			rows = limit;
			role = new Role();
			role.setRoleName(roleName);
			
			// 使用PageHelper分页
			PageHelper.startPage(page, rows);
			List<Role> list = findAllRole(role);
			Page<Role> page = (Page<Role>)list;
			long total=page.getTotal();
			
			mv.addObject("count",total);
			mv.addObject("code", 0);
			mv.addObject("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("角色展示错误",e);
		}
		return mv;
	}
	
	
	private List<Role> findAllRole(Role role){
		try {
			return roleService.findRole(role);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("roleCombobox")
	public ModelAndView roleCombobox(){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			List<Role> list = findAllRole(new Role());
			mv.addObject("data", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mv;
	}
	
	
	@RequestMapping("reserveRole")
	public ModelAndView addUser(Role role){
		Long roleId = role.getRoleId();
		
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			//修改
			if (null!=roleId) {
				role.setRoleId(roleId);
				roleService.updateRole(role);
				mv.addObject("success", true);
				mv.addObject("msg", "修改成功");
			}else {
				// 没有重复可以添加
				if(roleService.existRoleWithRoleName(role.getRoleName())==null){  
					roleService.addRole(role);
					mv.addObject("success", true);
					mv.addObject("msg", "新增成功");
				} else {
					mv.addObject("success", false);
					mv.addObject("msg", "该角色名被使用");
				}
			}
		} catch (Exception e) {
			logger.error("角色保存错误",e);
			mv.addObject("success", false);
			mv.addObject("msg", "对不起，操作失败");
		}
		
		return mv;
	}
	
	
	@RequestMapping("deleteRole")
	public ModelAndView delRole(String deleteRoleId){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			//添加非空判断
			String[] roleIds=deleteRoleId.split(",");
			for (int i=0;i<roleIds.length;i++) {
				PageHelper.startPage(1, 1);
				//该角色下面有用户
				User roleUser = (User) userService.existUserWithRoleId(Long.parseLong(roleIds[i])); 
				if(null!=roleUser){
					mv.addObject("success",false);
					mv.addObject("index", i);
					mv.addObject("msg", "有用户引用此角色，不能删除");
					return mv;
				}
			}
			if (roleIds.length==1) {
				roleService.deleteRole(Long.parseLong(roleIds[0]));
			} else {
				map = new HashMap(64);
				map.put("roleIds", roleIds);
				roleService.deleteRoleByRoleIds(map);
			}
			mv.addObject("success", true);
			mv.addObject("msg", "删除成功");
			mv.addObject("delNums", roleIds.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("角色删除错误",e);
			mv.addObject("success", false);
			mv.addObject("msg", "对不起，删除失败");
		}
	
		return mv;
	}
	
	
	
	@RequestMapping("chooseMenu")
	public ModelAndView chooseMenu(String roleId, String parentId){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			role = roleService.findOneRole(Long.parseLong(roleId));
			String menuIds = role.getMenuIds();
			String operationIds = role.getOperationIds();
			JSONArray jsonArray=getCheckedMenusByParentId(parentId, menuIds,operationIds);
			JSONArray jsonArrayResult = new JSONArray();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				Object dataValue = jsonObject.get("data");
				if(null != dataValue) {
				}
			 	String resultStr = jsonObject.toJSONString().replaceAll("\"spread\":\"open\",", ",");
			 	jsonObject  = JSONObject.parseObject(resultStr);
				jsonArrayResult.add(jsonObject);
			}
			
			mv.addObject(jsonArrayResult);
			
		} catch (Exception e) {
			logger.error("加载权限菜单树错误",e);
		}
		
		return mv;
	}
	
	/**
	 * 判断是不是叶子节点
	 * @param parentId
	 * @param menuIds
	 * @param operationIds
	 * @return
	 * @throws Exception
	 */
	private JSONArray getCheckedMenusByParentId(String parentId,String menuIds,String operationIds)throws Exception{
		JSONArray jsonArray=this.getCheckedMenuByParentId(parentId,menuIds,operationIds);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if("open".equals(jsonObject.getString("spread"))){
				continue;
			}else{
				jsonObject.put("data", getCheckedMenusByParentId(jsonObject.getString("value"),menuIds,operationIds));
			}
		}
		
		return jsonArray;
	}
	
	private JSONArray getCheckedMenuByParentId(String parentId,String menuIds,String operationIds)throws Exception{
		JSONArray jsonArray=new JSONArray();
		menu = new Menu();
		menu.setParentId(Long.parseLong(parentId));
		List<Menu> list = menuService.findMenu(menu);
		for(Menu menu : list){
			JSONObject jsonObject = new JSONObject();
			Long menuId = menu.getMenuId();
			jsonObject.put("value", menuId);
			jsonObject.put("title", menu.getMenuName());
			jsonObject.put("spread", menu.getState());
			if (StringUtil.isNotEmpty(menuIds)) {
				if (com.zit.cac.util.StringUtil.existStrArr(menuId+"", menuIds.split(","))) {
					jsonObject.put("checked", true);
				} 	
			}
			jsonObject.put("data", getOperationJsonArray(menuId,operationIds));
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	
	private JSONArray getOperationJsonArray(Long menuId,String operationIds){
		JSONArray jsonArray=new JSONArray();
		try {
			operation = new Operation();
			operation.setMenuId(menuId);
			List<Operation> list = operationService.findOperation(operation);
			for(Operation operation : list){
				JSONObject jsonObject = new JSONObject();
				Long operationId = operation.getOperationId();
				jsonObject.put("value", operationId);
				jsonObject.put("title", operation.getOperationName());
				jsonObject.put("spread", "open");
				jsonObject.put("data", "");
				if (StringUtil.isNotEmpty(operationIds)) {
					if (com.zit.cac.util.StringUtil.existStrArr(operationId+"", operationIds.split(","))) {
						jsonObject.put("checked", true);
					} 	
				}
				jsonArray.add(jsonObject);
			}
			return jsonArray;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	@RequestMapping("updateRoleMenu")
	public ModelAndView updateRoleMenu(String roleId,String menuStr){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			String[] ids = menuStr.split(",");
			String operationIds = "";
			String menuIds = "";
			/**
			 * 采用的方案是在菜单递归之后，再加上各菜单下的按钮
			 * 采用easyui的解析方式所以字段都用的是id和text。
			 * 为了区别两者，我们规定operationId自增从10000开始
			 * menuId从1开始，在上传过来的ids中是这样的形式
			 * 2,10000,3,4,7,10004,10006,45 这样的菜单ID和按钮ID的混合形式
			 * 所以通过与10000的比较来确定哪些是菜单的哪些是按钮的
			 * 
			 */
			for (int i = 0; i < ids.length; i++) {
				int id = Integer.parseInt(ids[i]);
				if (id>=10000) {
					operationIds += (","+id);
				} else {
					menuIds += (","+id);
				}
			}
			role = new Role();
			role.setRoleId(Long.parseLong(roleId));
			if (menuIds.length() != 0) {
				role.setMenuIds(menuIds.substring(1));
			}
			if (operationIds.length()!=0) {
				role.setOperationIds(operationIds.substring(1));
			}
			roleService.updateRole(role);
			
			
			mv.addObject("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("授权失败",e);
			mv.addObject("errorMsg", "对不起，授权失败");
		}
		return mv;
	}
	
	/**
	 * 返回角色信息弹出层
	 * @return
	 */
	@RequestMapping("roleInfoPop/{id}")
	public ModelAndView roleInfoPop(@PathVariable Long id, HttpSession session){
		Role role = null;
			try {
				if(null!=id) {
					role = roleService.findOneRole(id);
					String roleObjStr = JSON.toJSONString(role);
					session.setAttribute("data", roleObjStr);
				}else {
					session.setAttribute("data", "null");
				}
			} catch (Exception e) {
				logger.error("查询单条角色信息错误",e);
			}
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/pop/role_pop");
		return mv;
	}
	
	
	/**
	 * 根据选中末级节点获取所有的选中的父级节点和末级节点menuId
	 * @return
	 */
	@RequestMapping("getAllCheckMenuId")
	@ResponseBody
	public ModelAndView getAllCheckMenuId(String checkedMenuIds){
		
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		if(StringUtil.isNotEmpty(checkedMenuIds)) {
			String[] menuIdArr = checkedMenuIds.split(",");
			Menu  menu = null;
			Set<String> menuIdSet = new HashSet<String>();
			try {
				for (int i = 0; i < menuIdArr.length; i++) {
					long multipleId = 0L;
					try {
						multipleId=Long.parseLong(menuIdArr[i]);
					}catch(Exception e) {
						continue;
					}
					//为了区分菜单id和权限id
					int operationIdNum = 10000;
					//获取“非按钮”叶子节点的父级节点menuId
					if(multipleId < operationIdNum) {
						menu =  menuService.findMenuById(multipleId);
						
						//获取二级菜单
						if(null!=menu && null != menu.getParentId()) {
							menuIdSet.add(menu.getParentId().toString());
							
							Menu parentMenu = menuService.findMenuById(menu.getParentId());
							
							//获取一级菜单
							if(null != parentMenu&& null != parentMenu.getParentId()) {
								menuIdSet.add(parentMenu.getParentId().toString());
								
								// 获得顶级菜单
								Menu rootMenu = menuService.findMenuById(parentMenu.getParentId());
								if(null != rootMenu&& null != rootMenu.getMenuId()) {
									menuIdSet.add(rootMenu.getMenuId().toString());
								}
								
							}
							
						}
						//获取"按钮"的父级节点的menuId
					}else if(multipleId >= operationIdNum){
						Operation operation = operationService.findOperationById(multipleId);
						if(null != operation && null != operation.getMenuId()) {
							menu =  menuService.findMenuById(operation.getMenuId());
							
							// 获取二级菜单id
							menuIdSet.add(operation.getMenuId().toString());
							
							// 获取一级菜单id
							if(null!=menu && null != menu.getParentId()) {
								menuIdSet.add(menu.getParentId().toString());
								
								// 获得一级菜单
								Menu firstMenu = menuService.findMenuById(menu.getParentId());
								
								// 获得顶级菜单
								if(null != firstMenu&& null != firstMenu.getParentId()) {
									menuIdSet.add(firstMenu.getParentId().toString());
								}
							}
							
						}
					}
					menuIdSet.add(menuIdArr[i]);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				logger.error("数据格式转换失败",e);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("获取菜单id出错--根据选中末级节点获取所有的选中的父级节点和末级节点menuId",e);
			}
			Object[] menuIdObj = menuIdSet.toArray();
			String menuIdsStr = StringUtils.join(menuIdObj, ",");
			//去掉 前台传递过来的字符串中肯能包含 空格、换行等特殊符号
			
			Matcher m = MyRegularExpression.blankPattern.matcher(menuIdsStr);
			menuIdsStr  = m.replaceAll("");
			
			mv.addObject(menuIdsStr);
		}
		return mv;
	}
	
}
