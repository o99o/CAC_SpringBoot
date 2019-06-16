package com.zit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
* <p>Title: Application</p>
* <p>Description: SpringBoot启动文件</p>
* <p>Company: ZIT</p>
* @author lijiangtao
* @date   2019年4月14日
* 注意事项整理：
* （1）、事务通过注解方式实现，参考现有实现；
* （2）、
*/
//去掉Redis自动配置
@SpringBootApplication(exclude={RedisAutoConfiguration.class,
		RedisRepositoriesAutoConfiguration.class
})
//<tx:annotation-driven />
@EnableTransactionManagement 
@EnableScheduling
@ComponentScan(basePackages = "com.zit") 
//配置DAO接口扫描包，多个之间使用,分隔
@MapperScan({"com.zit.cac.dao"}) 
//开启缓存，可以使用@Cacheable、@CacheEvict、@CachePut注解
@EnableCaching 
public class Application extends SpringBootServletInitializer {
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
