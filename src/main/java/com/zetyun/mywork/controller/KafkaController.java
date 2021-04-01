package com.zetyun.mywork.controller;

import com.zetyun.mywork.common.Result;
import com.zetyun.mywork.common.ResultCode;
import com.zetyun.mywork.domain.LogInfo;
import com.zetyun.mywork.domain.LogInfoTemp;
import com.zetyun.mywork.service.KafkaService;
import com.zetyun.mywork.service.LoggerService;
import com.zetyun.mywork.util.JsonTools;
import com.zetyun.mywork.util.RandomUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: dingxy
 * @create: 2021-03-29 15:01:25
 **/
@RestController
@Log4j2
@RequestMapping("/api/kafka")
public class KafkaController {

    /*@Autowired
    private KafkaProducerOld producer;*/

    @Autowired
    private KafkaService kafkaService;

    /*@GetMapping(value = "/send")
    public Result monitorTypeList(HttpServletRequest request) {
        try {
            producer.send("{\"orderId\":\"002\",\"price\":\"80\"}");
            return Result.success();
        } catch (Exception e) {
            log.error("系统错误",e);
            return Result.failure(ResultCode.SENSEDEAL_QUESTIONED);
        }
    }*/

    // @RequestParam
    @PostMapping(value = "/send")
    public Result senMessage(@RequestBody String message){
        try {
            kafkaService.sendMessage(message);
            return Result.success();
        } catch (Exception e) {
            log.error("系统错误",e);
            return Result.failure(ResultCode.SENSEDEAL_QUESTIONED);
        }
    }

    @PostMapping(value = "/send/batch")
    public Result senBatchMessage(){
        try {
            String[] levelArray = {"DEBUG","INFO","WARNING","ERROR"};
            String[] methodTypeArray = {"GET","POST"};
            String[] methodNamePathArray= {"getById","getAll","getByName","deleteById","deleteByName"};
            AtomicInteger atomicInteger = new AtomicInteger(0);

            for (int i=0; i<10; i++){
                int levelNum = RandomUtil.getRandomIntInRange(0, 3);
                int methodTypeNum = RandomUtil.getRandomIntInRange(0, 1);
                int methodPathNum = RandomUtil.getRandomIntInRange(0, 4);

                LogInfo logInfo = LogInfo.builder().id(String.valueOf(atomicInteger.getAndIncrement()))
                        .thread("thread-logThread-"+atomicInteger.getAndIncrement())
                        .methodNamePath(methodNamePathArray[methodPathNum])
                        .methodType(methodTypeArray[methodTypeNum]).startTime(100).endTime(200).time(100)
                        .status("success").level(levelArray[levelNum]).build();
                String message = JsonTools.createJsonString(logInfo);
                kafkaService.sendMessage(message);
            }
            return Result.success();
        } catch (Exception e) {
            log.error("系统错误",e);
            return Result.failure(ResultCode.SENSEDEAL_QUESTIONED);
        }
    }
}
