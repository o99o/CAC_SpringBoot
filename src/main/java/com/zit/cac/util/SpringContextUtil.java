package com.zit.cac.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * 
* <p>Title: SpringContextUtil</p>
* <p>Description: 根据上下文获取bean（暂未使用）</p>
* <p>Company: ZIT</p>
* @author LiJiangtao
* @date   2018年5月5日
 */

public class SpringContextUtil implements ApplicationContextAware {
	/**
	 *  Spring应用上下文环境
	 */
    private static ApplicationContext applicationContext; 
 
    /**
     *  下面的这个方法上加了@Override注解，原因是继承ApplicationContextAware接口是必须实现的方法
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }
 
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
 
    public static Object getBean(String name, Class requiredType) throws BeansException {
        return applicationContext.getBean(name, requiredType);
    }
 
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }
 
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }
 
    public static Class getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }
 
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }
}
