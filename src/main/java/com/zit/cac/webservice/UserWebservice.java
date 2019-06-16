package com.zit.cac.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.zit.cac.entity.User;

/**
* <p>Title: UserService</p>
* <p>Description: </p>
* <p>Company: ZIT</p>
* @author lijiangtao
* @date   2018年5月7日
*/
 
@WebService
public interface UserWebservice {
     
    /**
     * 使用@WebMethod注解标注WebServiceI接口中的方法
     * @param name
     * @return
     */
    @WebMethod
    String sayHello(@WebParam(name="name") String name);
     
    /**
     * 查询用户
     * @param name
     * @return
     */
    @WebMethod
    User getUserByName(String name);
 
}
