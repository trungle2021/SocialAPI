package com.social.server.entities.Post;

import com.social.server.entities.User.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "post_tagged_users", schema = "social")
@IdClass(PostTaggedUsersPK.class)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostTaggedUsers {

    @Id
    @Column(name = "tagged_user_id", nullable = false, length = 36)
    private String taggedUserId;
    @Id
    @Column(name = "post_id", nullable = false, length = 36)
    private String postId;
    @Basic
    @Column(name = "tagged_at", nullable = true)
    private Instant taggedAt;
    @Basic
    @Column(name = "update_at", nullable = true)
    private Instant updateAt;
    @ManyToOne
    @JoinColumn(name = "tagged_user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Users usersByTaggedUserId;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Posts postsByPostId;


}
