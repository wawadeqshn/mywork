package com.zetyun.mywork.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 公共信息使用
 * @author: dingxy
 * @create: 2021-03-30 16:59:17
 **/
@Data
@Builder
public class CommonInfo implements Serializable {
    private String key;
    private String value;
}
