package com.example.socialmediaproject.services;

import com.example.socialmediaproject.dtos.FriendDTO;
import com.example.socialmediaproject.dtos.FriendListDTO;
import com.example.socialmediaproject.dtos.MutualFriendDTO;
import com.example.socialmediaproject.entities.Friends;
import com.example.socialmediaproject.entities.Users;

import java.util.HashSet;
import java.util.List;

public interface FriendService {

    FriendListDTO getFriendList(String userId);
    MutualFriendDTO getMutualFriend(String userId, String partnerId);
}
