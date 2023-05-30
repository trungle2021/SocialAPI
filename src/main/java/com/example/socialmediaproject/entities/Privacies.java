package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Entity
@Data
public class Privacies {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "privacy_type", nullable = true, length = 45)
    private String privacyType;
    @OneToMany(mappedBy = "privaciesByPrivacyStatusId")
    private Collection<UserMaritalStatus> userMaritalStatusesById;
    @OneToMany(mappedBy = "privaciesByPrivacyStatusId")
    private Collection<UserWorkplaces> userWorkplacesById;
    @OneToMany(mappedBy = "privaciesByPrivacyStatusId")
    private Collection<UserSchools> userSchoolsById;
    @OneToMany(mappedBy = "privaciesByPrivacyStatus")
    private Collection<Posts> postsById;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
