//package com.social.server.configs.ElasticSearch;
//
//import jakarta.validation.constraints.NotNull;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
//
//@Configuration
////@EnableJpaRepositories(basePackages = "com.social.server.repositories.JpaRepositories")
////@EnableElasticsearchRepositories(basePackages = "com.social.server.repositories.ElasticsearchRepositories")
//public class ElasticSearchConfiguration extends ElasticsearchConfiguration {
//
//    @Value("${spring.elasticsearch.uris}")
//    private String elasticsearchUrl;
//
//    @Override
//    public @NotNull ClientConfiguration clientConfiguration() {
//        return ClientConfiguration.builder()
//                .connectedTo(elasticsearchUrl)
//                .build();
//    }
//}
