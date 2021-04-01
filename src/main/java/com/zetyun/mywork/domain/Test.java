package com.zetyun.mywork.domain;

import com.zetyun.mywork.util.JsonTools;
import com.zetyun.mywork.util.RandomUtil;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: dingxy
 * @create: 2021-04-01 09:51:54
 **/
public class Test {
    public static void main(String[] args) {
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
                    .methodType(methodTypeArray[methodTypeNum])
                    .startTime(100)
                    .endTime(200)
                    .time(100)
                    .status("success")
                    .level(levelArray[levelNum])
                    .build();
            String msg = JsonTools.createJsonString(logInfo);
            System.out.println("---->"+msg);
        }

    }
}
