package com.zetyun.mywork.controller;

import com.zetyun.mywork.common.Result;
import com.zetyun.mywork.common.ResultCode;
import com.zetyun.mywork.domain.LogInfoTemp;
import com.zetyun.mywork.service.KafkaService;
import com.zetyun.mywork.service.LoggerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private LoggerService loggerService;

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

    @GetMapping(value = "/find")
    public Result find(){
        try {
            List<LogInfoTemp> result = loggerService.findAll();
            return Result.success(result);
        } catch (Exception e) {
            log.error("系统错误",e);
            return Result.failure(ResultCode.SENSEDEAL_QUESTIONED);
        }
    }

}
