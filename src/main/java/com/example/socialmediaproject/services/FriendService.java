package com.example.socialmediaproject.services;

import com.example.socialmediaproject.entities.Friends;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendService {

    List<Friends> getFriendList(String userId);
    List<Friends> getMutalFriend(String userId, String partnerId);
    int countMutalFriend(String userId, String partnerId);
}
