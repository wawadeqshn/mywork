package com.zetyun.mywork.service.impl;

import com.zetyun.mywork.service.KafkaService;
import com.zetyun.mywork.util.KafkaSenderUtils;
import org.springframework.stereotype.Service;

/**
 * @description: 业务实现类
 * @author: dingxy
 * @create: 2021-03-29 17:07:05
 **/
@Service
public class KafkaServiceImpl implements KafkaService {

    @Override
    public void sendMessage(String message) {
        KafkaSenderUtils.sendMessage(message);
    }
}
