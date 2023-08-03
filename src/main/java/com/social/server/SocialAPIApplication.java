package com.social.server;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
//@EnableJpaRepositories(basePackages = "com.social.server.repositories.JPA")
//@EnableElasticsearchRepositories(basePackages = "com.social.server.repositories.ElasticSearch")
@EnableJpaRepositories(
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = JpaRepository.class),excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = ElasticsearchRepository.class))
@EnableElasticsearchRepositories(
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = ElasticsearchRepository.class),excludeFilters =  @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE, classes = JpaRepository.class))
public class SocialAPIApplication {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {

        SpringApplication.run(SocialAPIApplication.class, args);
    }
}

