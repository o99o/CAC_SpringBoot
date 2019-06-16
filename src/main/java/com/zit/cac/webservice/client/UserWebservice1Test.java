package com.zit.cac.webservice.client;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zit.cac.entity.User;
import com.zit.cac.webservice.UserWebservice;

/**
* <p>Title: UserWebserviceTest</p>
* <p>Description: </p>
* <p>Company: ZIT</p>
* @author lijiangtao
* @date   2018年5月7日
*/
public class UserWebservice1Test {
	 /**
     * 客户端调用webService的方法，如果spring.xml中已配置下面部分：
     * <jaxws:endpoint id="userWebservice" address="/userWebservice"
        implementor="com.bsit.webservice.impl.UserWebserviceImpl" /> 
     * 则调用时factory.getBean("client")，
     * 强制转换成webService的接口
    */
	@Test
    public void testOrderWebService(){
        ApplicationContext factory = 
        		new ClassPathXmlApplicationContext("/spring-mybatis.xml");
        //转换OrderWebService
        UserWebservice client = (UserWebservice)factory.getBean("client");
         
        User user = new User();
        user = client.getUserByName("1");
        Assert.assertEquals("1", user.getUserName());
    }
}
