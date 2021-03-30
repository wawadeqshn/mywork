package com.zetyun.mywork.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zetyun.mywork.domain.LogInfo;
import com.zetyun.mywork.service.LoggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LoggerService loggerService;

    @KafkaListener(topics = "${spring.kafka.topic}",containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        System.out.println("接收到："+message);
        LogInfo info = JSON.parseObject(message, LogInfo.class);
        System.out.println("=========66=============> "+info.getMethodType());
        loggerService.save("",info);
    }
}
