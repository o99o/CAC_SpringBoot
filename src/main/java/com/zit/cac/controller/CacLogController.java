package com.zit.cac.controller;


import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zit.cac.entity.Log;
import com.zit.cac.service.LogService;
import com.zit.cac.util.Jackson2Util;
import com.zit.cac.util.PropertiesUtil;

/**
 *@author: o99o
 *@date: 2015-8-3上午08:51:00
 *@version:
 *@description：
 */
@Controller
@RequestMapping("cacLog")
public class CacLogController{
	
	private Log log;
	private int page = 1;
	private int rows = 10;
	@Autowired
	private LogService<Log> logService;
	
	static Logger logger = LogManager.getLogger(CacLogController.class);
	
	
	
	@RequestMapping("cacLogIndex")
	public ModelAndView indexLog(){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("zlits_cac/cac_log");
		return mv;
	}
	
	
	@RequestMapping("cacLogList")
	public ModelAndView logList(Integer curr,Integer limit,String module,String end,
			String operation,String start,String userName
			){
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		try {
			page = curr==null?1:curr;
			rows = limit==null?1:limit;
			
			log = new Log();
			log.setModule(module);
			log.setEnd(end);
			log.setOperation(operation);
			log.setStart(start);
			log.setUserName(userName);
			
			// 使用PageHelper分页
			PageHelper.startPage(page, rows);
			
			List<Log> list = logService.findLog(log);
			Page<Log> pageLog = (Page<Log>)list;
			long total=pageLog.getTotal();
			
			mv.addObject("count", total);
			mv.addObject("code", 0);
			mv.addObject("data", list);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	
	/**
	 * 批量删除
	 * @param request
	 * @param response
	 */
	@RequestMapping("deleteLog")
	public ModelAndView delLog(String deleteLogId) {
		ModelAndView mv=new ModelAndView();
		mv.setView(Jackson2Util.jsonView());
		
		try {
			String[] ids = deleteLogId.split(",");
			for (String id : ids) {
				logService.deleteLog(Long.parseLong(id));
			}	
			
			mv.addObject("success", true);
			mv.addObject("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("errorMsg", "对不起，删除失败");
		}
		
		return mv;
	}
	
	


	@RequestMapping("downloadLog4j")
	public ResponseEntity<byte[]> downloadLog4j() throws Exception{
		String path = PropertiesUtil.url;
		File file = new File(path);
		if(file.exists()) {
			HttpHeaders headers = new HttpHeaders();
			String fileName = new String("cac.log".getBytes("UTF-8"), "iso-8859-1");
			headers.setContentDispositionFormData("attachment", fileName);
			MediaType mediaType = new MediaType("application","octet-stream",Charset.forName("utf-8"));
			headers.setContentType(mediaType);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
		}else {
			logger.error("下载失败：文件不存在");
		}
		
		// 输出指定值
		return null;
	}

	
}