package com.example.socialmediaproject.dtos;

import com.example.socialmediaproject.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendListDTO {
    List<FriendDTO> friendList;
    Integer numberOfFriend;
    String friendListOwnerId;
}
