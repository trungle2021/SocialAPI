package com.example.socialmediaproject.dtos;

import com.example.socialmediaproject.entities.Users;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FriendListDTO {
    List<FriendDTO> friendList;
    Integer numberOfFriend;
    String friendListOwnerId;
}
