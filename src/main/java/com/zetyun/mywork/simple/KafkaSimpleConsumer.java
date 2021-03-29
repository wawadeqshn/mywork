package com.zetyun.mywork.simple;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @description: 简单消费者
 * @author: dingxy
 * @create: 2021-03-29 14:02:25
 **/
//@Component
public class KafkaSimpleConsumer {
    // 消费监听
    //@KafkaListener(topics = { "mytest" })
    public void consumer(ConsumerRecord<String, Object> consumerRecord) {
        System.out.println("消息消费--》" + consumerRecord.value().getClass().getName() + "数据 -->" + consumerRecord.value());
    }
}
