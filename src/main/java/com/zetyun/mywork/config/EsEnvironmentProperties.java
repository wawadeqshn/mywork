package com.zetyun.mywork.config;

import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: es配置
 * @author: dingxy
 * @create: 2021-03-29 19:38:27
 **/
@Configuration
public class EsEnvironmentProperties {

    @Value("${es.eshost}")
    private String host; // ES请求地址

    @Value("${es.port}")
    private int port; //端口

    @Value("${es.protocol}")
    private String protocol; //协议

    @Bean(name = "highLevelClientPre")
    public RestHighLevelClient getRestHighLevelClientPre() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port, HttpHost.DEFAULT_SCHEME_NAME));
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        builder.setHttpClientConfigCallback(f -> f.setDefaultCredentialsProvider(credentialsProvider));
        builder.setRequestConfigCallback(
                requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(5000)
                        .setSocketTimeout(6000000)).setMaxRetryTimeoutMillis(6000000);
        return new RestHighLevelClient(builder);
    }
}
