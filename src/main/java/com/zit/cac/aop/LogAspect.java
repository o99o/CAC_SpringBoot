package com.zit.cac.aop;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zit.cac.entity.Log;
import com.zit.cac.entity.User;
import com.zit.cac.service.LogService;
import com.zit.cac.util.IpUtil;
import com.zit.cac.util.TimeUtil;


@Aspect
@Component
public class LogAspect {
	
	@Autowired
	private LogService<Log> logService;
	
	/** 
     * 添加业务逻辑方法切入点 
     */ 
	@Pointcut("execution(* com.zit.cac.service.*.add*(..))")  
	public void addServiceCall() { 
	}  
	
	/** 
     * 修改业务逻辑方法切入点 
     */  
    @Pointcut("execution(* com.zit.cac.service.*.update*(..))")  
    public void updateServiceCall() {
    }  
    
    
    /** 
     * 删除业务逻辑方法切入点 
     * 此处拦截要拦截到具体的莫一个模块
     * 如deleteUser方法。则删除user的时候会记录日志
     * deleteRole时删除role会记录日志
     */  
    @Pointcut("execution(* com.zit.cac.service.*.delete*(..))")  
    public void deleteServiceCall() {
    }  
      
	
    /** 
     * 管理员添加操作日志(后置通知) 
     * @param joinPoint 
     * @param rtv 
     * @throws Throwable 
     */  
    @AfterReturning(value="addServiceCall()", argNames="rtv", returning="rtv")  
    public void insertServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable{  
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	User currentUser = (User) request.getSession().getAttribute("currentUser");
        //判断参数  
        if(joinPoint.getArgs() == null){
            return;  
        }  
        //获取方法名  
        //String methodName = joinPoint.getSignature().getName();  
        String className = joinPoint.getArgs()[0].getClass().getName();
        //获取操作内容  
		className = className.substring(className.lastIndexOf(".") + 1);
        String opContent = adminOptionContent(joinPoint.getArgs(), "添加");  
         
        //创建日志对象  
        Log log = new Log();
        log.setModule(className.toLowerCase());
        log.setUserName(currentUser.getUserName());
        //操作时间 
        log.setCreateTime(TimeUtil.formatTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
        //操作内容  
        log.setContent(opContent);
        //操作
        log.setOperation("添加");
        log.setIp(IpUtil.getIpAddr(request));
        logService.insertLog(log);
    }  
    
    
    /** 
     * 管理员修改操作日志(后置通知) 
     * @param joinPoint 
     * @param rtv 
     * @throws Throwable 
     */ 
    @AfterReturning(value="updateServiceCall()", argNames="rtv", returning="rtv")  
    public void updateServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable{  
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	User currentUser = (User) request.getSession().getAttribute("currentUser");
       
          
        //判断参数  
        if(joinPoint.getArgs() == null){ 
            return;  
        }  
        //获取方法名  
        String className = joinPoint.getArgs()[0].getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1);
        //获取操作内容  
        String opContent = adminOptionContent(joinPoint.getArgs(), "修改");  
        Log log = new Log();  
        log.setModule(className.toLowerCase());
        log.setUserName(currentUser.getUserName()); 
        //操作时间  
        log.setCreateTime(TimeUtil.formatTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
        //操作
        log.setContent(opContent);
        log.setOperation("修改");
        log.setIp(IpUtil.getIpAddr(request));
        //添加日志  
        logService.insertLog(log);
    }  
    
    /** 
     * 管理员删除操作日志(后置通知) 
     * @param joinPoint 
     * @param rtv 
     * @throws Throwable 
     */ 
    @AfterReturning(value="deleteServiceCall()", argNames="rtv", returning="rtv")  
    public void deleteServiceCallCalls(JoinPoint joinPoint, Object rtv) throws Throwable{  
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	User currentUser = (User) request.getSession().getAttribute("currentUser");
       
          
        //判断参数  
        if(joinPoint.getArgs() == null){ 
            return;  
        }  
        //获取方法名  
        String className = joinPoint.getTarget().getClass().getName().toLowerCase();
        try {
        	className=className.substring(0,className.indexOf("serviceimpl"));
        	className = className.substring(className.lastIndexOf(".") + 1);
        }catch(Exception e) {
        	e.printStackTrace();
        }
        //获取操作内容  
        String opContent = "ID:"+joinPoint.getArgs()[0].toString()+"->删除";  
        Log log = new Log();  
        log.setModule(className);
        log.setUserName(currentUser.getUserName()); 
        //操作时间  
        log.setCreateTime(TimeUtil.formatTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
        //操作
        log.setContent(opContent);
        log.setOperation("删除");
        log.setIp(IpUtil.getIpAddr(request));
        //添加日志  
        logService.insertLog(log);
    } 
    
    
	/**
	 * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
	 */
	private String adminOptionContent(Object[] args, String type) throws Exception {
		if (args == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		Object info = args[0];
		String className = info.getClass().getName();
		className = className.substring(className.lastIndexOf(".") + 1);
		sb.append(type+className+" 属性名和值：");
		// 获取对象的所有方法
		Method[] methods = info.getClass().getDeclaredMethods();
		// 遍历方法，判断get方法
		for (Method method : methods) {
			String methodName = method.getName();
			// 判断是不是get方法
			if (methodName.indexOf("get") == -1) {
				continue;// 不处理
			}
			Object rsValue = null;
			try {
				// 调用get方法，获取返回值
				rsValue = method.invoke(info);
				if (rsValue == null) {
					continue;
				}
			} catch (Exception e) {
				continue;
			}
			// 将值加入内容中
			sb.append(" " + methodName.substring(3) + "-->" + rsValue + "  ");
		}
		return sb.toString();
	}
	
}
