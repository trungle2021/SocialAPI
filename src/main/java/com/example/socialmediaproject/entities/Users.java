package com.example.socialmediaproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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
    @JsonManagedReference
    private Collection<CommentImagePosts> commentImagePostsById;

    @OneToMany(mappedBy = "usersByUserId")
    @JsonManagedReference
    private Collection<CommentPosts> commentPostsById;




    @OneToMany(mappedBy = "usersByUserId")
    @JsonManagedReference
    private Collection<LikeCommentPosts> likeCommentPostsById;
    @JsonManagedReference
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<LikeCommentsImage> likeCommentsImagesById;
    @JsonManagedReference
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<LikeImagePosts> likeImagePostsById;
    @JsonManagedReference
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<LikePost> likePostsById;
    @JsonManagedReference
    @OneToMany(mappedBy = "usersByUserPartnerId")
    private Collection<UserMaritalStatus> userMaritalStatusesById;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<UserWorkplaces> userWorkplacesById;



    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Accounts accountsByAccountId;

    @OneToMany(mappedBy = "usersByUserId")
    private Collection<UserSchools> userSchoolsById;
    @JsonManagedReference
    @OneToMany(mappedBy = "usersByTaggedUserId")
    private Collection<PostTaggedUsers> postTaggedUsersById;
    @JsonManagedReference
    @OneToMany(mappedBy = "usersByPostOwner")
    private Collection<Posts> postsById;
    @JsonManagedReference
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<SharedPosts> sharedPostsById;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
