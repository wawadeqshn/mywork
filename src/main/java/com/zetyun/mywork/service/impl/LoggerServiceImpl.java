package com.zetyun.mywork.service.impl;

import com.alibaba.fastjson.JSON;
import com.zetyun.mywork.common.CommonInfo;
import com.zetyun.mywork.domain.LogInfo;
import com.zetyun.mywork.domain.LogInfoTemp;
import com.zetyun.mywork.service.LoggerService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: 日志处理实现类
 * @author: dingxy
 * @create: 2021-03-29 21:51:16
 **/
@Service
@Slf4j
public class LoggerServiceImpl implements LoggerService {

    private static final String REQUEST_TYPE ="post";
    private static final String INDEX_NAME ="logindex";
    private static final String INDEX_TYPE ="logtype";
    private static final String GROUP_NAME ="group_by_level";
    private static final String GROUP_TYPE ="level";

    @Autowired
    @Qualifier("highLevelClientPre")
    private RestHighLevelClient highLevelClientPre;

    @Override
    public List<LogInfoTemp> findAll() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder sentimentQueryBuilder = QueryBuilders.termQuery("sales", "10"); // -1 负面，0中性，1正面

        boolQueryBuilder.must(sentimentQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(30); // 每页300条数据

        SearchRequest searchRequest = new SearchRequest("myindex");  // smart_news_sentiment_tag
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = highLevelClientPre.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (SearchHit hit : searchResponse.getHits()) {
            String content = hit.getSourceAsString();
            System.out.println(content);
        }

        return null;
    }

    /**
     * @description: 单个保存
     * @param indexName
     * @param bulk
     * @author: dingxy
     * @date: 2021/3/30 14:04
     */
    public IndexResponse save(String indexName, LogInfo bulk) {
        try{
            IndexRequest request = new IndexRequest(REQUEST_TYPE);
            request.index(indexName).type(INDEX_TYPE).id(String.valueOf(bulk.getId())).source(JSON.parseObject(JSON.toJSONString(bulk)), XContentType.JSON);
            IndexResponse response = highLevelClientPre.index(request, RequestOptions.DEFAULT);
            log.info("【新增】======【ElasticSearch 】 ~ 成功======");
            return response;
        }catch (Exception e){
            log.error("【新增】======【ElasticSearch 】 - 失败:{}", e);
        }
        return null;
    }


    /**
     * @description: 批量插入
     * @param indexName
     * @param bulks
     * @author: dingxy
     * @date: 2021/3/30 14:03
     */
    @Override
    public BulkResponse batchSave(String indexName, List<LogInfo> bulks) {

        try{
            BulkRequest bulkRequest = new BulkRequest();
            IndexRequest request = null;
            for(LogInfo bulk: bulks) {
                request = new IndexRequest(REQUEST_TYPE);
                request.index(indexName).id(String.valueOf(bulk.getId())).source(bulk.toString(), XContentType.JSON);
                bulkRequest.add(request);
            }
            BulkResponse response = highLevelClientPre.bulk(bulkRequest, RequestOptions.DEFAULT);
            return response;
        }catch (Exception e){
            log.error("批量插入索引失败:{}", e);
        }
        return null;
    }

    /**
     * @description: 分组查询，根据日志级别分组
     * @param
     * @author: dingxy
     * @date: 2021/3/30 16:24
     */
    @Override
    public List<CommonInfo> groupByLevel() {
        List<CommonInfo> result = new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(0); // 每页10条数据
        TermsAggregationBuilder aggBuilders = AggregationBuilders.terms(GROUP_NAME).field(GROUP_TYPE).size(200);
        searchSourceBuilder.aggregation(aggBuilders);
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = highLevelClientPre.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggs = searchResponse.getAggregations();
            Map<String, Aggregation> aggrMap = aggs.getAsMap();
            ParsedStringTerms aggValue = (ParsedStringTerms) aggrMap.get(GROUP_NAME);
            List<? extends Terms.Bucket> buckets = aggValue.getBuckets();
            for (Terms.Bucket bucket : buckets) {
                String key = bucket.getKey().toString(); // 日志类型
                long docCount = bucket.getDocCount(); // 对应的数量
                result.add(CommonInfo.builder().key(key).value(String.valueOf(docCount)).build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
