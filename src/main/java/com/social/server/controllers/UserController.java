package com.social.server.controllers;

import com.social.server.dtos.FriendListDTO;
import com.social.server.dtos.MutualFriendDTO;
import com.social.server.entities.User.FriendRequest;
import com.social.server.entities.User.Users;
import com.social.server.services.FriendService;
import com.social.server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
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
        friendService.sendFriendRequest(friendRequest);
    }

    @PostMapping("/acceptFriendRequest")
    public void acceptFriendRequest(@RequestBody FriendRequest friendRequest){
        friendService.acceptFriendRequest(friendRequest);
    }
}
