package com.zetyun.mywork.util;

import com.zetyun.mywork.product.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description: 发送消息的工具类
 * @author: dingxy
 * @create: 2021-03-29 16:57:39
 **/
@Component
public class KafkaSenderUtils {

    private static MessageProducer staticMessageProducer;

    @Autowired
    private MessageProducer messageProducer;

    @PostConstruct
    public void init() {
        staticMessageProducer = messageProducer;
    }

    public static void sendMessage(String message) {
        staticMessageProducer.sendMessage(message);
    }
}
