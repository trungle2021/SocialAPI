package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "comment_image_posts", schema = "social")
@IdClass(CommentImagePostsPK.class)
@Data
public class CommentImagePosts {
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
    @Column(name = "content", nullable = true, length = -1)
    private String content;
    @Basic
    @Column(name = "posted_at", nullable = true)
    private Timestamp postedAt;
    @Basic
    @Column(name = "update_at", nullable = true)
    private Timestamp updateAt;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Users usersByUserId;
    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private PostImages postImagesByImageId;
    @ManyToOne
    @JoinColumn(name = "id", referencedColumnName = "comment_id", nullable = false, insertable = false, updatable = false)
    private LikeCommentsImage likeCommentsImageById;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
