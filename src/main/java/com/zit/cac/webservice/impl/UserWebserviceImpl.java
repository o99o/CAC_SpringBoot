package com.zit.cac.webservice.impl;

import javax.jws.WebService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.zit.cac.entity.User;
import com.zit.cac.service.UserService;
import com.zit.cac.webservice.UserWebservice;

/**
* <p>Title: OrderWebServiceImpl</p>
* <p>Description: </p>
* <p>Company: ZIT</p>
* @author lijiangtao
* @date   2018年5月7日
*/
@WebService(endpointInterface="com.zit.cac.webservice.UserWebservice",
serviceName="userWebservice")
public class UserWebserviceImpl implements UserWebservice {

	static Logger logger = LogManager.getLogger(UserWebserviceImpl.class);
	
	@Autowired
	private UserService<User> userService;
	
	@Override
	public String sayHello(String name) {
		System.out.println("WebService sayHello " + name);
        return "Hello " + name + ", nice to meet you.";
	}

	@Override
	public User getUserByName(String name) {
		User user=null;
		try {
			user=userService.existUserWithUserName(name);
		} catch (Exception e) {
			logger.error("查询用户："+name+" 失败.", e);
		}
		return user;
	}
}
