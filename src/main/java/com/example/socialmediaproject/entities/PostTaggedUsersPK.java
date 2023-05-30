package com.example.socialmediaproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class PostTaggedUsersPK implements Serializable {
    @Column(name = "tagged_user_id", nullable = false, length = 36)
    @Id
    private String taggedUserId;
    @Column(name = "post_id", nullable = false, length = 36)
    @Id
    private String postId;

}
