package com.zit.cac.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zit.cac.entity.Log;
import com.zit.cac.entity.Role;
import com.zit.cac.entity.Token;
import com.zit.cac.entity.User;
import com.zit.cac.service.LogService;
import com.zit.cac.service.RoleService;
import com.zit.cac.service.TokenService;
import com.zit.cac.service.UserService;
import com.zit.cac.util.IpUtil;
import com.zit.cac.util.MagicValue;
import com.zit.cac.util.TimeUtil;

/**
 *@author: o99o
 *@date: 2015-8-15下午12:39:50
 *@version:
 *@description：登录拦截器
 */
@SuppressWarnings("unchecked")
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	
	private Map map;
	private Log log;
	private Token token;
	private Role role;
	@Autowired
	private RoleService<Role> roleService;
	@Autowired
	private UserService<User> userService;
	@Autowired
	private LogService<Log> logService;
	@Autowired
	private TokenService<Token> tokenService;
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 请求的路径
		String url = request.getServletPath().toString();
		HttpSession session = request.getSession();
		User currentUser = ((User) session.getAttribute("currentUser"));
		if (currentUser == null) {
			String urlLowerCase=url.toLowerCase();
			if (urlLowerCase.contains(MagicValue.LOGIN.getDesc())||
					urlLowerCase.contains(MagicValue.WEBSERVICE.getDesc())) {
				return true;
			}
			response.sendRedirect("/login.jsp");
	        return true;
		}
		return true;
	}
}
