package com.social.server.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CommentPostsPK implements Serializable {
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
