package com.zetyun.mywork.service.impl;

import com.zetyun.mywork.domain.LogInfo;
import com.zetyun.mywork.domain.LogInfoTemp;
import com.zetyun.mywork.service.KafkaService;
import com.zetyun.mywork.service.LoggerService;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 日志处理实现类
 * @author: dingxy
 * @create: 2021-03-29 21:51:16
 **/
@Service
public class LoggerServiceImpl implements LoggerService {


    @Autowired
    @Qualifier("highLevelClientPre")
    private RestHighLevelClient highLevelClientPre;

    @Override
    public List<LogInfoTemp> findAll() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //long startTime = getStartTime(days);


        TermQueryBuilder sentimentQueryBuilder = QueryBuilders.termQuery("sales", "10"); // -1 负面，0中性，1正面

        // TermQueryBuilder companyQueryBuilder = QueryBuilders.termQuery("tags.industry.keyword", industry_name);
        //TermsQueryBuilder companyQueryBuilder = QueryBuilders.termsQuery("tags.industry.keyword",secondIndustryList);
        //NestedQueryBuilder companyNestedQueryBuilder = QueryBuilders.nestedQuery("tags", companyQueryBuilder, ScoreMode.Total);
        //boolQueryBuilder.must(companyNestedQueryBuilder).must(sentimentQueryBuilder);// minimumShouldMatch(1);
        //searchSourceBuilder.query(boolQueryBuilder).sort("publish_time", SortOrder.DESC);
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
}
