package com.zit.cac.kafka.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.alibaba.fastjson.JSON;
import com.zit.cac.kafka.constant.KafkaMesConstant;

/**
 * kafkaProducer模板
 * 	使用此模板发送消息
 * @author zhangye
 *
 */
@Component
public class KafkaProducerServer{

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public KafkaTemplate<String, String> getKafkaTemplate() {
		return kafkaTemplate;
	}

	public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	/**
	 * kafka发送消息模板
	 * @param topic 主题
	 * @param value	messageValue
	 * @param ifPartition 是否使用分区 0是\1不是
	 * @param partitionNum 分区数 如果是否使用分区为0,分区数必须大于0
	 * @param role 角色:bbc app erp...
	 */
	public Map<String,Object> sndMesForTemplate(String topic, Object value, String ifPartition, 
			Integer partitionNum, String role){
		
		
		String key = role+"-"+value.hashCode();
		String valueString = JSON.toJSONString(value);
		if(ifPartition.equals("0")){
			//表示使用分区
			int partitionIndex = getPartitionIndex(key, partitionNum);
			ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, partitionIndex, key, valueString);
			Map<String,Object> res = checkProRecord(result);
			return res;
		}else{
			ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, key, valueString);
			Map<String,Object> res = checkProRecord(result);
			return res;
		}
	}

	/**
	 * 根据key值获取分区索引
	 * @param key
	 * @param partitionNum
	 * @return
	 */
	private int getPartitionIndex(String key, int partitionNum){
		if (key == null) {
			Random random = new Random();
			return random.nextInt(partitionNum);
		}
		else {
			int result = Math.abs(key.hashCode())%partitionNum;
			return result;
		}
	}
	
	/**
	 * 检查发送返回结果record
	 * @param res
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Map<String,Object> checkProRecord(ListenableFuture<SendResult<String, String>> res){
		Map<String,Object> m = new HashMap<String,Object>();
		if(res!=null){
			try {
				SendResult r = res.get();//检查result结果集
				/*检查recordMetadata的offset数据，不检查producerRecord*/
				Long offsetIndex = r.getRecordMetadata().offset();
				if(offsetIndex!=null && offsetIndex>=0){
					m.put("code", KafkaMesConstant.SUCCESS_CODE);
					m.put("message", KafkaMesConstant.SUCCESS_MES);
					return m;
				}else{
					m.put("code", KafkaMesConstant.KAFKA_NO_OFFSET_CODE);
					m.put("message", KafkaMesConstant.KAFKA_NO_OFFSET_MES);
					return m;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				m.put("code", KafkaMesConstant.KAFKA_SEND_ERROR_CODE);
				m.put("message", KafkaMesConstant.KAFKA_SEND_ERROR_MES);
				return m;
			} catch (ExecutionException e) {
				e.printStackTrace();
				m.put("code", KafkaMesConstant.KAFKA_SEND_ERROR_CODE);
				m.put("message", KafkaMesConstant.KAFKA_SEND_ERROR_MES);
				return m;
			}
		}else{
			m.put("code", KafkaMesConstant.KAFKA_NO_RESULT_CODE);
			m.put("message", KafkaMesConstant.KAFKA_NO_RESULT_MES);
			return m;
		}
	}
	
	public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:kafkaProducer.xml");
        KafkaTemplate kafkaTemplate = ctx.getBean("kafkaTemplate", KafkaTemplate.class);
        for (int i = 1; ; i++) {
            String msg = "msg-" + i;
            //向topicOne发送消息
            kafkaTemplate.send("topicOne", msg);
            System.out.println("send msg  : " + msg);
        }
    }

}
