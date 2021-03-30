package com.zetyun.mywork.service;

import com.zetyun.mywork.common.CommonInfo;
import com.zetyun.mywork.domain.LogInfo;
import com.zetyun.mywork.domain.LogInfoTemp;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;

import java.util.List;

/**
 * @description: 日志处理类
 * @author: dingxy
 * @create: 2021-03-29 21:50:35
 **/
public interface LoggerService {

    List<LogInfoTemp> findAll();

    /**
     * @description: 单个保存
     * @param indexName
     * @param bulk
     * @author: dingxy
     * @date: 2021/3/30 16:23
     */
    IndexResponse save(String indexName, LogInfo bulk);

    /**
     * @description: 批量保存
     * @param indexName
     * @param bulks
     * @author: dingxy
     * @date: 2021/3/30 16:24
     */
    BulkResponse batchSave(String indexName, List<LogInfo> bulks);

    /**
     * @description: 查询分组
     * @param
     * @author: dingxy
     * @date: 2021/3/30 16:24
     */
    List<CommonInfo> groupByLevel();
}
