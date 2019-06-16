package com.zit.cac;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zit.cac.kafka.producer.KafkaProducerServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class KafkaTest {
	
	@Autowired
	private KafkaProducerServer kafkaProducerServer;
	
	@Test
	public void test() {
		String topic = "topicOne";
		String value = "test";
		String ifPartition = "0";
		Integer partitionNum = 3;
		String role = "test";//用来生成key
		Map<String,Object> res = kafkaProducerServer.sndMesForTemplate
				(topic, value, ifPartition, partitionNum, role);
		
		System.out.println("测试结果如下：===============");
		String message = (String)res.get("message");
		String code = (String)res.get("code");
		
		System.out.println("code:"+code);
		System.out.println("message:"+message);
	}
	
}
