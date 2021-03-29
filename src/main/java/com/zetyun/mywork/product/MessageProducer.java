package com.zetyun.mywork.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zetyun.mywork.config.KafkaConfigProperties;
import com.zetyun.mywork.util.ReturnJsonFormatUntil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

/**
 * @description: 用于发送消息
 * @author: dingxy
 * @create: 2021-03-29 16:51:42
 **/
@Component
@Slf4j
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @Resource
    private KafkaConfigProperties kafkaConfigProperties;

    public void sendMessage(Object obj) {
        ListenableFuture<SendResult<String, Object>> future  = null;
        try {
            String sendString = JSONObject.toJSONString(obj);
            log.info("准备发送消息为========> {}", sendString);
            // String jsonMessage = JSON.toJSONString(msg);
            future = kafkaTemplate.send(kafkaConfigProperties.getTopic(), obj);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(ReturnJsonFormatUntil.jsonString(false,"发送kafka日志信息，无法转化成FastJSON格式"));
        }

        if (future != null) {
            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

                @Override
                public void onFailure(Throwable ex) {
                    //System.out.println("发送kafka信息失败，失败信息：" + throwable.getMessage());
                    //log.error(ReturnJsonFormatUntil.jsonStrings(false,"发送kafka日志信息失败，失败信息：" + ex.getMessage(),msg));
                    //发送失败的处理
                    log.info(kafkaConfigProperties.getTopic() + " ======== 生产者 发送消息失败========：" + ex.getMessage());
                }

                @Override
                public void onSuccess(SendResult<String, Object> result) {
                    // log.info(ReturnJsonFormatUntil.jsonStrings(true,"发送kafka信息成功！！！", obj));
                    log.info(kafkaConfigProperties.getTopic() + " ======== 生产者 发送消息成功======== " + result.toString());
                    System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                            + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
                }
            });
        }
    }
}
