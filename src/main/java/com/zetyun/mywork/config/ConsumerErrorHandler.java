package com.zetyun.mywork.config;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;

/**
 * @description: 消费者消费失败处理
 * @author: dingxy
 * @create: 2021-04-01 11:00:45
 **/
@Configuration
public class ConsumerErrorHandler {

    @Bean
    public ConsumerAwareListenerErrorHandler dealError() {

        return new ConsumerAwareListenerErrorHandler() {
            @Override
            public Object handleError(Message<?> message, ListenerExecutionFailedException e, Consumer<?, ?> consumer) {
                System.out.println("consumer 消费失败=====> " + e);
                // TODO 将失败的记录保存到数据库，再用定时任务查询记录，并重刷数据
                return null;
            }
        };
    }
}
