package com.zit.cac.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zit.cac.entity.User;
import com.zit.cac.service.UserService;

/**
 * 
 * @author LiHongjie
 *
 * 2018年5月17日
 */

@Controller
@RequestMapping("layui")
public class LayUiController extends CacLogController{
	private int page;
	private int rows;
	private User user;
	@Autowired
	private UserService<User> userService;
	static Logger logger = LogManager.getLogger(LayUiController.class);
	
	
	@RequestMapping("tableIndex")
	public ModelAndView tableIndex(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/layui/layui_table");
		return mv;
	}
	
	@RequestMapping("formIndex")
	public ModelAndView formIndex(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/layui/layui_form");
		return mv;
	}
	@RequestMapping("uploadIndex")
	public ModelAndView uploadIndex(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/layui/layui_upload");
		return mv;
	}
	
	
	
}
