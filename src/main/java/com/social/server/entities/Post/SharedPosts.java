package com.social.server.entities.Post;

import com.social.server.entities.PK.SharedPostsPK;
import com.social.server.entities.User.Users;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "shared_posts", schema = "social")
@IdClass(SharedPostsPK.class)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SharedPosts {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "user_id", nullable = true, length = 36)
    private String userId;
    @Id
    @Column(name = "post_id", nullable = false, length = 36)
    private String postId;
    @Basic
    @Column(name = "shared_at", nullable = true)
    private Instant sharedAt;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Users usersByUserId;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Posts postsByPostId;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
