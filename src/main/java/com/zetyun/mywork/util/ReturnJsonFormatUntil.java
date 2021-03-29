package com.zetyun.mywork.util;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: JSON格式转换工具类
 * @author: dingxy
 * @create: 2021-03-29 16:48:55
 **/
public class ReturnJsonFormatUntil {
    public static String json(boolean flag, Object result){
        Map<String, Object> map = new HashMap<String, Object>();
        if (flag){
            map.put("success",true);
            map.put("message","请求成功");
            map.put("entity",result);
        }else{
            map.put("success",false);
            map.put("message",result);
            map.put("entity","");
        }

        //return JSONUtils.toJSONString(map);
        return JSON.toJSONString(map);
    }

    public static String jsonString(boolean flag, Object result){
        Map<String, Object> map = new HashMap<String, Object>();
        if (flag){
            map.put("success",true);
            map.put("message","请求成功");
            map.put("entity",result);
        }else{
            map.put("success",false);
            map.put("message",result);
            map.put("entity","");
        }

        return JSON.toJSONString(map);
    }
    public static String jsonStrings(boolean success, String message,Object result){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success",success);
        map.put("message",message);
        map.put("entity", result);

        return JSON.toJSONString(map);
    }
}
