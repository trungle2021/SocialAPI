package com.social.server.configs.ElasticSearch;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.social.server.repositories")
@ComponentScan(basePackages = { "com.social.server.services"})
public class ElasticSearchConfiguration extends AbstractElasticsearchConfiguration {

    @Value("{spring.elasticsearch.uris}")
    private String elasticsearchUrl;
    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        try {

            ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                    .connectedTo(elasticsearchUrl)
                    .build();

            return RestClients.create(clientConfiguration).rest();

        } catch (Exception e) {

            // Throw an unchecked exception
            throw new RuntimeException("Failed to create Elasticsearch client", e);

        }
    }
}
