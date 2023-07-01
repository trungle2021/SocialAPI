package com.example.socialmediaproject.configs.Kafka;

import com.example.socialmediaproject.entities.FriendRequest;
import com.google.gson.Gson;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "FriendRequest",groupId = "friendRequest")
   void friendRequestListener(FriendRequest request){
//        Gson gson = new Gson();
//        FriendRequest request = gson.fromJson(data,FriendRequest.class);
        System.out.println(String.format("Received a new friend request of %s and %s",request.getUserId(),request.getPartnerId()));
   }
}
