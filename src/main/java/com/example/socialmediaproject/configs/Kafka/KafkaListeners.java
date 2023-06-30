package com.example.socialmediaproject.configs.Kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(
            topics = "social",
            groupId = "groupId"
    )
    public void listeners(String data){
        System.out.println("Listener received: " + data);
    }
}
