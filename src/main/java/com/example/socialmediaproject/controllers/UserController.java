package com.example.socialmediaproject.controllers;

import com.example.socialmediaproject.dtos.FriendDTO;
import com.example.socialmediaproject.dtos.FriendListDTO;
import com.example.socialmediaproject.dtos.MutualFriendDTO;
import com.example.socialmediaproject.entities.Friends;
import com.example.socialmediaproject.entities.Users;
import com.example.socialmediaproject.services.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final FriendService friendService;

    @GetMapping("/getMutualFriends/{userId}/{partnerId}")
    public ResponseEntity<MutualFriendDTO> getMutualFriends(@PathVariable String userId, @PathVariable String partnerId){
        return ResponseEntity.ok(friendService.getMutualFriend(userId,partnerId));
    }

    @GetMapping("/getFriendList/{userId}")
    public ResponseEntity<FriendListDTO> getFriends(@PathVariable String userId){
        return ResponseEntity.ok(friendService.getFriendList(userId));
    }
}
