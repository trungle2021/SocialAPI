package com.social.server.dtos.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchDTO {
    private String username;
    private Integer countOfMutualFriend;
    private Boolean isFriend;
    private String avatar;
}
