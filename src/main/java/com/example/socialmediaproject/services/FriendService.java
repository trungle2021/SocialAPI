package com.example.socialmediaproject.services;

import com.example.socialmediaproject.entities.Friends;

import java.util.HashSet;
import java.util.List;

public interface FriendService {

    HashSet<Friends> getFriendList(String userId);
    List<Friends> getMutualFriend(String userId, String partnerId);
    int countMutualFriend(String userId, String partnerId);
}
