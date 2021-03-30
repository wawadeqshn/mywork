package com.zetyun.mywork.domain;

import lombok.Data;

/**
 * @description: 日志信息
 * @author: dingxy
 * @create: 2021-03-29 17:35:20
 **/
@Data
public class LogInfo {
    private String id; // 业务ID
    private String thread; // 线程
    private String methodNamePath; // 调用接口的类完整路径
    private String methodType; // 接口中调用的方法
    private long startTime; // 开始时间
    private long endTime; // 结束时间
    private long time; // 耗时
    private String status; // 状态
    private String level; // 级别 INFO
}