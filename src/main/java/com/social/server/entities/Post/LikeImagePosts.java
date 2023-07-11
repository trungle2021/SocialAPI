package com.social.server.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "like_image_posts", schema = "social")
@IdClass(LikeImagePostsPK.class)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeImagePosts {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "user_id", nullable = true, length = 36)
    private String userId;

    @Id
    @Column(name = "image_id", nullable = false, length = 36)
    private String imageId;
    @Basic
    @Column(name = "liked_at", nullable = true)
    private Timestamp likedAt;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Users usersByUserId;
    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private PostImages postImagesByImageId;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
