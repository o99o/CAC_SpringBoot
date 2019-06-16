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
import com.zit.cac.entity.User;
import com.zit.cac.service.UserService;
import com.zit.cac.util.Jackson2Util;

/**
 * 
 * @author LiHongjie
 *
 * 2018年5月17日
 */

@Controller
@RequestMapping("cacUser")
public class CacUserController {

	private int page;
	private int rows;
	private User user;
	@Autowired
	private UserService<User> userService;
	static Logger logger = LogManager.getLogger(CacUserController.class);
	
	@RequestMapping("cacUserIndex")
	public ModelAndView index(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/cac_user");
		return mv;
	}
	
	@RequestMapping("cacRoleIndex")
	public ModelAndView indexRole(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/cac_role");
		return mv;
	}
	
	@RequestMapping("userList")
	public ModelAndView userList(Integer curr,Integer limit,
			String userName,Long roleId){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			page=curr==null?1:curr;
			rows=limit==null?10:limit;
			user = new User();
			user.setUserName(userName);
			user.setRoleId(roleId);
			
			// 使用PageHelper分页
			PageHelper.startPage(page, rows);
			List<User> list= userService.findUser(user);
			Page<User> page = (Page<User>)list;
			long total=page.getTotal();
			mv.addObject("count",total );
			mv.addObject("code", 0);
			mv.addObject("data", list);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
		}
		
		return mv;
	}
	
	
	/**
	 * 新增或修改
	 * @param request
	 * @param user
	 * @param response
	 */
	@RequestMapping("reserveUser")
	public ModelAndView reserveUser(User user){
		Long userId = user.getUserId();
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			// userId不为空 说明是修改
			if (null!=userId) { 
				user.setUserId(userId);
				userService.updateUser(user);
				mv.addObject("success", true);
				mv.addObject("msg", "修改成功");
			}else {   
				// 添加
				if(userService.existUserWithUserName(user.getUserName())==null){ 
					userService.addUser(user);
					mv.addObject("success", true);
					mv.addObject("msg", "新增成功");
				} else {
					mv.addObject("success",false);
					mv.addObject("msg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			mv.addObject("msg", "对不起，操作失败");
		}
		return mv;
	}
	
	
	@RequestMapping("deleteUser")
	public ModelAndView delUser(String deleteUserId){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			String[] ids=deleteUserId.split(",");
			for (String id : ids) {
				userService.deleteUser(Long.parseLong(id));
			}
			mv.addObject("success", true);
			mv.addObject("msg","删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			mv.addObject("success", false);
			mv.addObject("msg", "对不起，删除失败");
		}
		
		return mv;
	}
	
	/**
	 * 返回角色弹出层
	 * @return
	 */
	@RequestMapping("userRolePop")
	public ModelAndView userRolePop(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/pop/user_role_pop");
		return mv;
	}
	
	/**
	 * 返回用户信息弹出层
	 * @return
	 */
	@RequestMapping("userInfoPop/{id}")
	public ModelAndView userInfoPop(@PathVariable Long id, HttpSession session){
		User user = null;
		try {
			user = userService.findOneUserAndRole(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询单条用户信息错误",e);
		}
		String userObjStr = JSON.toJSONString(user);
		session.setAttribute("data", userObjStr);
		
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/pop/user_pop");
		return mv;
	}
}
