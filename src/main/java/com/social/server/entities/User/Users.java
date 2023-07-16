package com.social.server.entities.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.social.server.entities.Post.*;
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
    @GeneratedValue(strategy = GenerationType.UUID)
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
    @Column(name = "username", nullable = true, length = 45)
    private String username;

    @Basic
    @Column(name = "account_id", nullable = true, length = 36)
    private String accountId;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;

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
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<SharedPosts> sharedPostsById;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
