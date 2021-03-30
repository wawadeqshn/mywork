package com.zetyun.mywork.service;

import com.zetyun.mywork.domain.LogInfo;
import com.zetyun.mywork.domain.LogInfoTemp;
import org.elasticsearch.action.bulk.BulkResponse;

import java.util.List;

/**
 * @description: 日志处理类
 * @author: dingxy
 * @create: 2021-03-29 21:50:35
 **/
public interface LoggerService {

    List<LogInfoTemp> findAll();

    BulkResponse save(String indexName, List<LogInfo> bulks);
}
