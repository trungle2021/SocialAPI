package com.example.socialmediaproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class FriendsPK implements Serializable {
    @Column(name = "user_id", nullable = false, length = 36)
    @Id
    private String userId;
    @Column(name = "user_friend_id", nullable = false, length = 36)
    @Id
    private String userFriendId;
}
