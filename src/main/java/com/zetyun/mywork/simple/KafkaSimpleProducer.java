package com.zetyun.mywork.simple;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @description: 简单生产者
 * @author: dingxy
 * @create: 2021-03-29 14:00:56
 **/
//@Component
//@EnableScheduling
public class KafkaSimpleProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 定时任务
     */
    //@Scheduled(cron = "0/10 * * * * ?")
    public void send() {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("mytest",
                UUID.randomUUID().toString());
        future.addCallback(o -> System.out.println("send-消息发送成功"), throwable -> System.out.println("消息发送失败："));
    }
}
