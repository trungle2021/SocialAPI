package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CommentImagePostsPK implements Serializable {
    @Column(name = "id", nullable = false, length = 36)
    @Id
    private String id;
    @Column(name = "image_id", nullable = false, length = 36)
    @Id
    private String imageId;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
