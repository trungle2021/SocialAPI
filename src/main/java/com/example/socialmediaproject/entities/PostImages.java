package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "post_images", schema = "social")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostImages {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "image_url", nullable = true, length = 45)
    private String imageUrl;
    @Basic
    @Column(name = "post_id", nullable = true, length = 36)
    private String postId;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;
    @OneToMany(mappedBy = "postImagesByImageId")
    private Collection<CommentImagePosts> commentImagePostsById;
    @OneToMany(mappedBy = "postImagesByImageId")
    private Collection<LikeImagePosts> likeImagePostsById;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Posts postsByPostId;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
