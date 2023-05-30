package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class SharedPostsPK implements Serializable {
    @Column(name = "id", nullable = false, length = 36)
    @Id
    private String id;
    @Column(name = "post_id", nullable = false, length = 36)
    @Id
    private String postId;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

}
