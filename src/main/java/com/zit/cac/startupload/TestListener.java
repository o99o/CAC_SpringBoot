package com.zit.cac.startupload;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;

import com.zit.cac.cache.CacheTest;
import com.zit.cac.entity.User;
import com.zit.cac.service.UserService;

/**
 * 
* <p>Title: TestListener</p>
* <p>Description: </p>
* <p>Company: ZIT</p>
* @author LiJiangtao
* @date   2018年6月12日
 */
@Controller
public class TestListener implements ServletContextAware{
	private static Logger logger = LogManager
			.getLogger(TestListener.class);
	
	@Autowired 
	private UserService userService=null;


	@Override
	public void setServletContext(ServletContext arg0) {
		// 测试启动时加载数据库功能
		try {
			User user=(User) userService.findOneUser(1L);
			System.out.println(user);
			// 测试缓存
			CacheTest.INSTANCE.cacheUserName=user.getUserName();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
