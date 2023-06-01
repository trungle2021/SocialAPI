package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Collection;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Users {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "first_name", nullable = true, length = 30)
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = true, length = 30)
    private String lastName;
    @Basic
    @Column(name = "phone", nullable = true, length = 15)
    private String phone;
    @Basic
    @Column(name = "gender", nullable = true, length = 10)
    private String gender;
    @Basic
    @Column(name = "dob", nullable = true)
    private Date dob;
    @Basic
    @Column(name = "address", nullable = true, length = 45)
    private String address;
    @Basic
    @Column(name = "origin", nullable = true, length = 45)
    private String origin;
    @Basic
    @Column(name = "account_id", nullable = true, length = 36)
    private String accountId;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<CommentImagePosts> commentImagePostsById;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<CommentPosts> commentPostsById;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<Friends> friendsById;
    @OneToMany(mappedBy = "usersByUserFriendId")
    private Collection<Friends> friendsById_0;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<LikeCommentPosts> likeCommentPostsById;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<LikeCommentsImage> likeCommentsImagesById;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<LikeImagePosts> likeImagePostsById;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<LikePost> likePostsById;
    @OneToOne(mappedBy = "usersByUserId")
    private UserMaritalStatus userMaritalStatusById;
    @OneToMany(mappedBy = "usersByUserPartnerId")
    private Collection<UserMaritalStatus> userMaritalStatusesById;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<UserWorkplaces> userWorkplacesById;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Accounts accountsByAccountId;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<UserSchools> userSchoolsById;
    @OneToMany(mappedBy = "usersByTaggedUserId")
    private Collection<PostTaggedUsers> postTaggedUsersById;
    @OneToMany(mappedBy = "usersByPostOwner")
    private Collection<Posts> postsById;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<SharedPosts> sharedPostsById;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
