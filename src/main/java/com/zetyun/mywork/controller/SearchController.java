package com.zetyun.mywork.controller;

import com.zetyun.mywork.common.Result;
import com.zetyun.mywork.common.ResultCode;
import com.zetyun.mywork.domain.LogInfoTemp;
import com.zetyun.mywork.service.LoggerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
