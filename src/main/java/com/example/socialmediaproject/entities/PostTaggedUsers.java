package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "post_tagged_users", schema = "social")
@IdClass(PostTaggedUsersPK.class)
@Data
public class PostTaggedUsers {

    @Id
    @Column(name = "tagged_user_id", nullable = false, length = 36)
    private String taggedUserId;
    @Id
    @Column(name = "post_id", nullable = false, length = 36)
    private String postId;
    @Basic
    @Column(name = "tagged_at", nullable = true)
    private Timestamp taggedAt;
    @Basic
    @Column(name = "update_at", nullable = true)
    private Timestamp updateAt;
    @ManyToOne
    @JoinColumn(name = "tagged_user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Users usersByTaggedUserId;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Posts postsByPostId;


}
