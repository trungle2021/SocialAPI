package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@IdClass(FriendsPK.class)
@Data
public class Friends {

    @Id
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Id
    @Column(name = "user_friend_id", nullable = false, length = 36)
    private String userFriendId;
    @Basic
    @Column(name = "establish_at", nullable = true)
    private Timestamp establishAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Users usersByUserId;
    @ManyToOne
    @JoinColumn(name = "user_friend_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Users usersByUserFriendId;


}
