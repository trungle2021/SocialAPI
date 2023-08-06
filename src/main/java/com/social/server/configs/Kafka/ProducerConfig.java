//package com.social.server.configs.Kafka;
//
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class ProducerConfig {
//
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;
//
//    public Map<String, Object> producerConfig(){
//        Map<String, Object> props = new HashMap<>();
//        props.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        props.put(JsonSerializer.TYPE_MAPPINGS, "friendRequest:com.social.server.entities.User.FriendRequest");
//        return props;
//    }
//
//    @Bean
//    public ProducerFactory<String, Object> producerFactory(){
//        return new DefaultKafkaProducerFactory<>(producerConfig());
//    }
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory){
//        return new KafkaTemplate<>(producerFactory);
//    }
//}
