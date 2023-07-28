package com.social.server.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class FriendDTO {
    private String id;
    private String accountId;
    private String firstName;
    private String lastName;
    private Boolean isDeleted;
    private Long mutualFriend;

    public FriendDTO(String id, String firstName, String lastName, String accountId, Boolean isDeleted, Long mutualFriend) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountId = accountId;
        this.isDeleted = isDeleted;
        this.mutualFriend = mutualFriend;
    }

    public FriendDTO(String id, String firstName, String lastName, String accountId, Boolean isDeleted) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accountId = accountId;
        this.isDeleted = isDeleted;
    }
}
