package com.social.server.entities.Post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(LikePK.class)
public class Likes {

    @Basic
    @Id
    @Column(name = "user_id", nullable = true, length = 36)
    private String userId;
    @Id
    @Column(name = "parent_id", nullable = false, length = 36)
    private String parentId;
    @Basic
    @Column(name = "liked_at", nullable = true)
    private Instant likedAt;

}
