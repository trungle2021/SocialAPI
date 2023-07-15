package com.social.server.entities.Post;

import com.social.server.entities.PK.LikePK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@IdClass(LikePK.class)
public class Likes {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Basic
    @Id
    @Column(name = "user_id", nullable = true, length = 36)
    private String userId;
    @Id
    @Column(name = "post_base_id", nullable = false, length = 36)
    private String postBaseId;
    @Basic
    @Column(name = "liked_at", nullable = true)
    private Timestamp likedAt;

}
