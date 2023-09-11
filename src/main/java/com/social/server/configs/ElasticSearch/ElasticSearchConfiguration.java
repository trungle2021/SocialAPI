package com.social.server.configs.ElasticSearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.validation.constraints.NotNull;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ElasticSearchConfiguration{

    @Value("${spring.elasticsearch.uris}")
    private String elasticsearchUrl;

    @Bean
    public @NotNull ElasticsearchClient clientConfiguration() {
//
//        RestClient restClient = RestClient
//                .builder(HttpHost.create(elasticsearchUrl))
//                .build();

        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "https")).build();
        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }






}
