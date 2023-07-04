package com.social.server.services;
import com.social.server.dtos.FriendListDTO;
import com.social.server.dtos.MutualFriendDTO;


public interface FriendService {

    FriendListDTO getFriendListByStatus(String userId, String status);
    MutualFriendDTO getMutualFriend(String userId, String partnerId);
}
