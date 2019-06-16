package com.zit.cac.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.zit.cac.entity.Log;
import com.zit.cac.entity.Menu;
import com.zit.cac.entity.Role;
import com.zit.cac.entity.Token;
import com.zit.cac.entity.User;
import com.zit.cac.service.LogService;
import com.zit.cac.service.MenuService;
import com.zit.cac.service.RoleService;
import com.zit.cac.service.TokenService;
import com.zit.cac.service.UserService;
import com.zit.cac.util.IpUtil;
import com.zit.cac.util.Jackson2Util;
import com.zit.cac.util.MagicValue;
import com.zit.cac.util.ScreenSpec;
import com.zit.cac.util.StringUtil;
import com.zit.cac.util.TimeUtil;
import com.zit.cac.util.WriterUtil;

/**
 *@author: wangq
 *@date: 2015-5-18下午04:27:10
 *@version:
 *@description：
 */
@Controller
@RequestMapping("cacInto")
@SuppressWarnings("unchecked")
public class CacLoginController {
	static Logger logger = LogManager.getLogger(CacLoginController.class);

	private User user;
	private User currentUser;
	@Autowired
	private UserService<User> userService;
	@Autowired
	private MenuService<Menu> menuService;
	private Role role;
	@Autowired
	private RoleService<Role> roleService;
	private Map map;
	@Autowired
	private LogService<Log> logService;
	private Log log;
	@Autowired
	private TokenService<Token> tokenService;
	private Token token;
	
	@RequestMapping("login")
	public void login(String userName,String password,String imageCode,
			String auto,HttpServletRequest request,
			HttpServletResponse response){
		
		try {
			HttpSession session = request.getSession();
			request.setAttribute("userName", userName);
			request.setAttribute("password", password);
			request.setAttribute("imageCode", imageCode);
			if(StringUtil.isEmpty(userName)||StringUtil.isEmpty(password)){
				request.setAttribute("error", "账户或密码为空");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			map = new HashMap<String, String>(2);
			map.put("userName", userName);
			map.put("password", password);
			try {
				currentUser = userService.loginUser(map);
				session.setAttribute("currentUser", currentUser);
			}catch(Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "数据库连接失败！");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			if(currentUser==null){
				request.setAttribute("error", "用户名或密码错误");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}else {
				saveLogAndSession(userName, request, session);  
				response.sendRedirect(ScreenSpec.MAIN1680+"");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户登录错误",e);
		}
	}
	
	/**
	 * 保存日志和Session
	 * @param userName
	 * @param request
	 * @param session
	 * @throws Exception
	 */
	private void saveLogAndSession(String userName, HttpServletRequest request, HttpSession session) throws Exception {
		// 加入登陆日志
		log = new Log();
		log.setUserName(userName);
		log.setCreateTime(TimeUtil.formatTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
		log.setIp(IpUtil.getIpAddr(request));
		log.setOperation("登录");
		logService.insertLog(log);
		
		// 登录信息存入session
		role = roleService.findOneRole(currentUser.getRoleId());
		String roleName=role.getRoleName();
		currentUser.setRoleName(roleName);
		// 当前用户信息
		session.setAttribute("currentUser", currentUser);  
		// 当前用户所拥有的按钮权限
		session.setAttribute("currentOperationIds", role.getOperationIds());
	}
	
	
	/**
	 * 进入系统主界面
	 * @return
	 */
	@RequestMapping(ScreenSpec.MAIN1680)
	public ModelAndView toMain1680(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/"+ScreenSpec.MAIN1680);
		return mv;
	}
	
	
	/**
	 * 加载最上级左菜单树
	 * @param request
	 * @param response
	 */
	@RequestMapping("menuTree")
	public ModelAndView getMenuTree(HttpServletRequest request,String parentId){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			currentUser = (User) request.getSession().getAttribute("currentUser");
			if(null!=currentUser) {
				role = roleService.findOneRole(currentUser.getRoleId());
				String[] menuIds = role.getMenuIds().split(",");
				map = new HashMap(128);
				map.put("parentId",parentId);
				map.put("menuIds", menuIds);
				JSONArray jsonArray = getMenusByParentId(parentId, menuIds);
				Object[] objArr = jsonArray.toArray();
				mv.addObject(objArr[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("加载左菜单错误",e);
		}
		
		return mv;
	}
	
	
	/**
	 * 递归加载所所有树菜单
	 * @param parentId
	 * @param menuIds
	 * @return
	 * @throws Exception
	 */
	private JSONArray getMenusByParentId(String parentId,String[] menuIds)throws Exception{
		JSONArray jsonArray=this.getMenuByParentId(parentId,menuIds);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if("open".equals(jsonObject.getString("state"))){
				continue;
			}else{
				jsonObject.put("children", getMenusByParentId(jsonObject.getString("id"),menuIds));
			}
		}
		return jsonArray;
	}
	
	
	/**
	 * 将所有的树菜单放入easyui要求格式的json
	 * @param parentId
	 * @param menuIds
	 * @return
	 * @throws Exception
	 */
	private JSONArray getMenuByParentId(String parentId,String[] menuIds)throws Exception{
		JSONArray jsonArray=new JSONArray();
		map= new HashMap(128);
		map.put("parentId",parentId);
		map.put("menuIds", menuIds);
		List<Menu> list = menuService.menuTree(map);
		for(Menu menu : list){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", menu.getMenuId());
			jsonObject.put("text", menu.getMenuName());
			jsonObject.put("iconCls", menu.getIconCls());
			jsonObject.put("menuUrl", menu.getMenuUrl());
			if(!hasChildren(menu.getMenuId(), menuIds)){
				jsonObject.put("state", "open");
			}else{
				jsonObject.put("state", menu.getState());				
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	
	/**
	 * 判断是不是有孩子，人工结束递归树
	 * @param parentId
	 * @param menuIds
	 * @return
	 */
	private boolean hasChildren(Long parentId,String[] menuIds){
		boolean flag = false;
		try {
			map= new HashMap<Object, Object>(128);
			map.put("parentId",parentId);
			map.put("menuIds", menuIds);
			List<Menu> list = menuService.menuTree(map);
			if (list == null || list.size()==0) {
				flag = false;
			}else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("加载左菜单时判断是不是有子孩子错误",e);
		}
		return flag;
	}

	/**
	 * 跳转到修改密码的页面
	 * @param request
	 * @param response
	 */
	@RequestMapping("intoUpdatePassword")
	public ModelAndView intoUpdatePassword(HttpServletRequest request,HttpServletResponse response){
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/password_update");
		return mv;
	}
	
	
	
	/**
	 * 更新密码
	 * @param request
	 * @param response
	 */
	@RequestMapping("updatePassword")
	public ModelAndView updatePassword(HttpServletRequest request,String newPassword){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			User currentUser=(User) request.getSession().getAttribute("currentUser");
			Long userId = currentUser.getUserId();
			
			user=new User();
			user.setUserId(userId);
			user.setPassword(newPassword);
			userService.updateUser(user);
			mv.addObject("success", "true");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("密码更新失败",e);
			mv.addObject("errorMsg", "对不起！密码修改失败");
		}
		
		return mv;
	}
	
	
	/**
	 * 安全退出
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("cacLogout")
	private void logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		// 记录日志
		HttpSession session = request.getSession();
		if(null!=((User)session.getAttribute(MagicValue.CURRENT_USER.getDesc()))&&(null!=log)){
			log.setUserName(((User)session.getAttribute("currentUser")).getUserName());
			log.setCreateTime(TimeUtil.formatTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
			log.setOperation("退出");
			logService.insertLog(log);
		}
		
		try {
			// 清空session
			session.invalidate();
			
			// 清空cookie
			Cookie[] cookies = request.getCookies();
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = new Cookie(cookies[i].getName(), null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			
		}catch(Exception e) {
			logger.error("退出...");
		}
		
		return;// 优化勿删
		
	}
	
	
	
	/**
	 * 自动登录
	 * @param request
	 * @param response
	 */
	@RequestMapping("auto")
	public void autoLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	Cookie[] cookies = request.getCookies();
    	if(cookies == null) {
    		WriterUtil.writePlain(response, "no");
    	} else {
        	for(int i=0; i<cookies.length; i++) {
           		Cookie cookie = cookies[i];
           		if("autoLogin".equals(cookie.getName())){
					  map = new HashMap(4);
					  map.put("token", cookie.getValue());
					  map.put("expireTime", TimeUtil.formatTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
					  
					  // 使用PageHelper分页
					  PageHelper.startPage(1, 1);
					
					  token = tokenService.findOneToken(map);
					  if (token==null) {
						  WriterUtil.writePlain(response, "no");
					  } else {
						  	long userId = token.getUserId();
						  	currentUser = userService.findOneUser(userId);
						  	log = new Log();
							log.setUserName(currentUser.getUserName());
							log.setCreateTime(TimeUtil.formatTime(new Date(), "yyyy-MM-dd HH:mm:ss"));
							log.setIp(IpUtil.getIpAddr(request));
							log.setOperation("登录");
							logService.insertLog(log);
							
							// 登录信息存入session
							role = roleService.findOneRole(currentUser.getRoleId());
							String roleName=role.getRoleName();
							currentUser.setRoleName(roleName);
							HttpSession session = request.getSession();
							// 当前用户信息
							session.setAttribute("currentUser", currentUser);  
							// 当前用户所拥有的按钮权限
							session.setAttribute("currentOperationIds", role.getOperationIds());  
							// 跳转到主界面
							WriterUtil.writePlain(response, "main");
					  }
           		} else {
           			WriterUtil.writePlain(response, "no");
           		}
        	}
    	}
   
	}
}
