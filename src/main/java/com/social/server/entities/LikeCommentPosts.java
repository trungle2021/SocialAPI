package com.social.server.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "like_comment_posts", schema = "social")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeCommentPosts {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "comment_id", nullable = true, length = 36)
    private String commentId;
    @Basic
    @Column(name = "user_id", nullable = true, length = 36)
    private String userId;
    @Basic
    @Column(name = "liked_at", nullable = true)
    private Timestamp likedAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Users usersByUserId;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
