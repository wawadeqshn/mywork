package com.zetyun.mywork.util;

import com.alibaba.fastjson.JSONArray;

/**
 * @description: 对象转Json工具类
 * @author: dingxy
 * @create: 2021-04-01 10:07:21
 **/
public class JsonTools {
    public static String createJsonString(Object value) {
        Object objs = JSONArray.toJSON(value);
        String json = objs.toString();
        return json;
    }
}
