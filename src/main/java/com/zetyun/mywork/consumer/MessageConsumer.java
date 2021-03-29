package com.zetyun.mywork.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @description: 消费者监听服务
 * @author: dingxy
 * @create: 2021-03-29 17:01:08
 **/
@Component
@Slf4j
public class MessageConsumer {

    @KafkaListener(topics = "${spring.kafka.topic}",containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        System.out.println("接收到："+message);
    }
}
