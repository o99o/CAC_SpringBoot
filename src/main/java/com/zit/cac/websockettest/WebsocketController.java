package com.zit.cac.websockettest;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;




@Controller
public class WebsocketController {

    @RequestMapping("/websocket/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ModelAndView mView = new ModelAndView();
        String username = request.getParameter("username");
        System.out.println(username+"登录");
        
        if (username.equals("xiaoming") ) {
			
        	mView.setViewName("websocket");
		}else {
			mView.setViewName("websocket2");
		}
        
        return mView;
    }

   
}
