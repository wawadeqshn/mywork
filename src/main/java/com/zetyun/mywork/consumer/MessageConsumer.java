package com.zetyun.mywork.consumer;

import com.alibaba.fastjson.JSONArray;
import com.zetyun.mywork.domain.LogInfo;
import com.zetyun.mywork.service.LoggerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 消费者监听服务
 * @author: dingxy
 * @create: 2021-03-29 17:01:08
 **/
@Component
@Slf4j
public class MessageConsumer {

    private static final String INDEX_NAME ="logindex";

    @Autowired
    private LoggerService loggerService;

    //单个消费
    /*@KafkaListener(topics = "${spring.kafka.topic}",containerFactory = "kafkaListenerContainerFactory", errorHandler = "dealError", concurrency = "2")
    public void listen(String message) {
        System.out.println("【消费者】接收到消息=============> "+ message);
        LogInfo info = JSON.parseObject(message, LogInfo.class);
        String level = info.getLevel();
        loggerService.save(INDEX_NAME,info);
    }*/

    // 批量消费
    @KafkaListener(topics = "${spring.kafka.topic}",containerFactory = "kafkaListenerContainerFactory", errorHandler = "dealError")
    public void batchListen(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
        Set<String> messageSet = (Set) records.stream().map(ConsumerRecord::value).collect(Collectors.toSet());
        System.out.println("【消费者】接收到消息=============> "+ messageSet.toString());
        List<LogInfo> result = JSONArray.parseArray(messageSet.toString(), LogInfo.class);
        System.out.println("【转换后的数据】=============> "+ result);
        loggerService.batchSave(INDEX_NAME,result);
        ack.acknowledge();
    }
}
