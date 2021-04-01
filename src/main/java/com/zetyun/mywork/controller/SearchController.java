package com.zetyun.mywork.controller;

import com.alibaba.fastjson.JSON;
import com.zetyun.mywork.common.CommonInfo;
import com.zetyun.mywork.common.Result;
import com.zetyun.mywork.common.ResultCode;
import com.zetyun.mywork.domain.LogInfo;
import com.zetyun.mywork.domain.LogInfoTemp;
import com.zetyun.mywork.service.LoggerService;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 搜索接口提供类
 * @author: dingxy
 * @create: 2021-03-30 10:20:45
 **/
@RestController
@Log4j2
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private LoggerService loggerService;

    @GetMapping(value = "/group/type")
    public Result find(){
        try {
            List<CommonInfo> result = loggerService.groupByLevel();
            return Result.success(result);
        } catch (Exception e) {
            log.error("系统错误",e);
            return Result.failure(ResultCode.SENSEDEAL_QUESTIONED);
        }
    }

    @GetMapping(value = "/get/type")
    public Result findByType(@RequestParam String type){
        try {
            List<LogInfo> result = loggerService.findByType(type);
            return Result.success(result);
        } catch (Exception e) {
            log.error("系统错误",e);
            return Result.failure(ResultCode.SENSEDEAL_QUESTIONED);
        }
    }

    @PostMapping(value = "/save")
    public Result save(@RequestBody String message){
        try {
            System.out.println("接受请求参数=============> "+message);
            LogInfo info = JSON.parseObject(message, LogInfo.class);
            IndexResponse result = loggerService.save("logindex",info);
            return Result.success(result);
        } catch (Exception e) {
            log.error("系统错误",e);
            return Result.failure(ResultCode.SENSEDEAL_QUESTIONED);
        }
    }
}
