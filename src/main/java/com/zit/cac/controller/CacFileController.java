package com.zit.cac.controller;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * <p>Title: CacFileController</p>  
 * <p>Description:cac文件上传 </p>  
 * @author lihongjie  
 * @date 2018年6月21日
 */
@Controller
@RequestMapping("cacFile")
public class CacFileController{
	static Logger logger = LogManager.getLogger(CacFileController.class);
	@ResponseBody
	@RequestMapping("fixUpload")  
	public String fixUpload(HttpServletRequest request){
        //将当前上下文初始化给 CommonsMutipartResolver (多部分解析器)
        CommonsMultipartResolver multipartResolver = 
        		new CommonsMultipartResolver(request.getSession().getServletContext());
        
        JSONObject jsonObject = new JSONObject();
        
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            
            
            //获取multiRequest中所有的文件名
            Iterator iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //一次遍历所有的文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                
                if(null!= file && file.getSize()>20*1024*1024) {
                	jsonObject.put("msg", "传输文件过大，上传失败！");
                	return jsonObject.toString();	
                }
                System.out.println("file.getSize:"+file.getSize());
                if(file!=null){
                    String path = "C:/"+file.getOriginalFilename();
                    //上传
                    try {
						file.transferTo(new File(path));
						jsonObject.put("msg", true);
					} catch (IllegalStateException e) {
						logger.error("上传文件过程失败", e);
					} catch (IOException e) {
						logger.error("上传文件失败", e);
					}
                }
            }
        }
        return jsonObject.toString();
	}
}