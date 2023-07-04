package com.social.server.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FriendsPK implements Serializable {
    @Column(name = "user_id", nullable = false, length = 36)
    @Id
    private String userId;
    @Column(name = "user_friend_id", nullable = false, length = 36)
    @Id
    private String userFriendId;
}
