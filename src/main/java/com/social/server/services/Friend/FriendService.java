package com.social.server.services.Friend;
import com.social.server.dtos.FriendListDTO;
import com.social.server.dtos.MutualFriendDTO;
import com.social.server.entities.User.FriendRequest;


public interface FriendService {

    FriendListDTO getFriendListByStatus(String userId, String status);
    MutualFriendDTO getMutualFriend(String userId, String partnerId);
    void sendFriendRequest(FriendRequest friendRequest);
    void acceptFriendRequest(FriendRequest friendRequest);
}
