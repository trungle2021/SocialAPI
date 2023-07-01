package com.example.socialmediaproject.controllers;

import com.example.socialmediaproject.dtos.FriendListDTO;
import com.example.socialmediaproject.dtos.MutualFriendDTO;
import com.example.socialmediaproject.entities.FriendRequest;
import com.example.socialmediaproject.entities.Users;
import com.example.socialmediaproject.services.FriendService;
import com.example.socialmediaproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final KafkaTemplate kafkaTemplate;
    private final FriendService friendService;
    private final UserService userService;

    @GetMapping("/getMutualFriends/{userId}/{partnerId}")
    public ResponseEntity<MutualFriendDTO> getMutualFriends(@PathVariable String userId, @PathVariable String partnerId){
        return ResponseEntity.ok(friendService.getMutualFriend(userId,partnerId));
    }

    @GetMapping("/getFriendList/{userId}/{status}")
    public ResponseEntity<FriendListDTO> getFriendListByStatus(@PathVariable String userId, @PathVariable String status){
        return ResponseEntity.ok(friendService.getFriendListByStatus(userId,status));
    }

    @GetMapping("/getUserInfo/{userId}")
    public ResponseEntity<Users> getUserInfo(@PathVariable("userId") String userId){
        return ResponseEntity.ok(userService.getOneById(userId));
    }

    @PostMapping("/sendFriendRequest")
    public void sendFriendRequest(@RequestBody FriendRequest friendRequest){
        kafkaTemplate.send("FriendRequest",friendRequest);
    }
}
