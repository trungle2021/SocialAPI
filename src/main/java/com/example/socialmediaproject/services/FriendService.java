package com.example.socialmediaproject.services;
import com.example.socialmediaproject.dtos.FriendListDTO;
import com.example.socialmediaproject.dtos.MutualFriendDTO;


public interface FriendService {

    FriendListDTO getFriendListByStatus(String userId,String status);
    MutualFriendDTO getMutualFriend(String userId, String partnerId);
}
