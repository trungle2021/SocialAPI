package com.social.server.dtos;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MutualFriendDTO {
   List<FriendDTO> mutualFriendList;
   private Integer numberOfMutualFriend;
    private String ownerId;
    private String partnerId;
}
