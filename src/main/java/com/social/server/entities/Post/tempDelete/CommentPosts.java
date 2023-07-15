//package com.social.server.entities.Post;
//
//import com.social.server.entities.PK.CommentPostsPK;
//import com.social.server.entities.User.Users;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.sql.Timestamp;
//import java.util.UUID;
//
//@Entity
//@Table(name = "comment_posts", schema = "social")
//@IdClass(CommentPostsPK.class)
//@Builder
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//public class CommentPosts {
//
//    @Id
//    @Column(name = "id", nullable = false, length = 36)
//    private String id;
//    @Basic
//    @Column(name = "user_id", nullable = true, length = 36)
//    private String userId;
//    @Id
//    @Column(name = "post_id", nullable = false, length = 36)
//    private String postId;
//    @Basic
//    @Column(name = "content", nullable = true, length = -1)
//    private String content;
//    @Basic
//    @Column(name = "posted_at", nullable = true)
//    private Timestamp postedAt;
//    @Basic
//    @Column(name = "update_at", nullable = true)
//    private Timestamp updateAt;
//    @Basic
//    @Column(name = "is_deleted", nullable = true)
//    private Boolean isDeleted;
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private Users usersByUserId;
//    @ManyToOne
//    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
//    private Posts postsByPostId;
//    @ManyToOne
//    @JoinColumn(name = "id", referencedColumnName = "comment_id", nullable = false, insertable = false, updatable = false)
//    private LikeCommentPosts likeCommentPostsById;
//    @PrePersist
//    public void prePersist() {
//        this.id = UUID.randomUUID().toString();
//    }
//}
