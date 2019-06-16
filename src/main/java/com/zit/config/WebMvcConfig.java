package com.zit.config;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zit.cac.interceptor.LoginInterceptor;

/**
* <p>Title: WebMvcConfig</p>
* <p>Description: 与spring-mvc.xml对应</p>
* <p>Company: ZIT</p>
* @author lijiangtao
* @date   2019年4月14日
*/
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
	static Logger logger = LogManager.getLogger(WebMvcConfig.class);
    @Resource
    private MappingJackson2HttpMessageConverter converter;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.debug("开始注册静态资源");
        registry.addResourceHandler("/common/**").addResourceLocations("classpath:/static/common/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/layui/**").addResourceLocations("classpath:/static/layui/");
        registry.addResourceHandler("/style/**").addResourceLocations("classpath:/static/style/");
        
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/", ".jsp");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter);
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }
    
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	
    	registry.addInterceptor(new LoginInterceptor());
    	
    }

}