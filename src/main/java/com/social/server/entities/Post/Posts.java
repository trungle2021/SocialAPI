package com.social.server.entities.Post;

import com.social.server.entities.Privacies;
import com.social.server.entities.User.Users;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Posts {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "content", nullable = true, length = 300)
    private String content;
    @Basic
    @Column(name = "post_owner", nullable = true, length = 36)
    private String postOwner;
    @Basic
    @Column(name = "privacy_status", nullable = true, length = 36)
    private String privacyStatus;
    @Basic
    @Column(name = "posted_at", nullable = true)
    private Timestamp postedAt;
    @Basic
    @Column(name = "update_at", nullable = true)
    private Timestamp updateAt;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;
    @OneToMany(mappedBy = "postsByPostId")
    private Collection<CommentPosts> commentPostsById;
    @OneToMany(mappedBy = "postsByPostId")
    private Collection<LikePost> likePostsById;
    @OneToMany(mappedBy = "postsByPostId")
    private Collection<PostImages> postImagesById;
    @OneToMany(mappedBy = "postsByPostId")
    private Collection<PostTaggedUsers> postTaggedUsersById;
    @ManyToOne
    @JoinColumn(name = "post_owner", referencedColumnName = "id", insertable = false, updatable = false)
    private Users usersByPostOwner;
    @ManyToOne
    @JoinColumn(name = "privacy_status", referencedColumnName = "id", insertable = false, updatable = false)
    private Privacies privaciesByPrivacyStatus;
    @OneToMany(mappedBy = "postsByPostId")
    private Collection<SharedPosts> sharedPostsById;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
