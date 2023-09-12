package com.social.server.configs.Kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {
    @Bean
    public NewTopic friendRequests(){
        return TopicBuilder.name("friend-requests")
                .build();
    }

    @Bean
    public NewTopic friendAcceptances(){
        return TopicBuilder.name("friend-acceptances")
                .build();
    }

}
