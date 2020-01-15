package com.zit.cac.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 *@author: o99o
 *@date: 2015-5-18上午11:46:02
 *@version:
 *@description：
 */
public class WriterUtil {
	
	public static void write(HttpServletResponse response,String obj){
		try {
			 response.setContentType("text/html; charset=utf-8");
			 PrintWriter out=response.getWriter();
			 out.println(obj);
			 out.flush();
			 out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writePlain(HttpServletResponse response,String obj){
		try {
			 response.setContentType("text/plain; charset=utf-8");
			 PrintWriter out=response.getWriter();
			 out.println(obj);
			 out.flush();
			 out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
