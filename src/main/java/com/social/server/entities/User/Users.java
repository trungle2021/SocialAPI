package com.social.server.entities.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.social.server.entities.Post.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.social.server.configs.ElasticSearch.Indices.USER_INDEX;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "users")
public class Users {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id", nullable = false, length = 36)
    @Field(type = FieldType.Keyword)
    private String id;
    @Basic
    @Column(name = "username", nullable = true, length = 45)
    @Field(type = FieldType.Text)
    private String username;
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

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Accounts accountsByAccountId;

    @OneToMany(mappedBy = "usersByUserId")
    private Collection<UserSchools> userSchoolsById;
    @OneToMany(mappedBy = "usersByTaggedUserId")
    private Collection<PostTaggedUsers> postTaggedUsersById;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
