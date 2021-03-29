package com.zetyun.mywork.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @description: kafka的相关属性
 * @author: dingxy
 * @create: 2021-03-29 16:44:33
 **/
@Data
@Configuration
public class KafkaConfigProperties {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Value("${spring.kafka.group-id}")
    private String groupId;
}
