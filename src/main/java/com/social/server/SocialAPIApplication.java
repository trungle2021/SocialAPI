package com.social.server;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
//@EnableJpaRepositories(
//        excludeFilters = @ComponentScan.Filter(
//        type = FilterType.ASSIGNABLE_TYPE, classes = ElasticsearchRepository.class))
//@EnableElasticsearchRepositories(
//        excludeFilters =  @ComponentScan.Filter(
//        type = FilterType.ASSIGNABLE_TYPE, classes = JpaRepository.class))
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

