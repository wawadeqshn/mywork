package com.zetyun.mywork.domain;

import lombok.Data;

/**
 * @description:
 * @author: dingxy
 * @create: 2021-03-29 22:03:34
 **/
@Data
public class LogInfoTemp {
    private String visitors; // 调用接口的类完整路径
    private String name; // 接口中调用的方法
    private long sales; // 开始时间
}
