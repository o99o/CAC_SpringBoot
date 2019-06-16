package com.zit.cac;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class JedisTest {

	@Autowired
	private JedisPool jedisPool;

	@Test
	public void testJedis2() {
		// 新增key
		Jedis jedis = jedisPool.getResource();
		jedis.set("pno", "100");
		String pnoValue = jedis.get("pno");
		System.out.println("pno的值是：" + pnoValue + "---------------");
		// 修改
		jedis.incrBy("pno", 10L);
		pnoValue = jedis.get("pno");
		System.out.println("pno的值是：" + pnoValue + "================");
	}
	

}
