package com.example.socialmediaproject.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_workplaces", schema = "social")
@IdClass(UserWorkplacesPK.class)
@Data
public class UserWorkplaces {

    @Id
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;
    @Id
    @Column(name = "workplace_id", nullable = false, length = 36)
    private String workplaceId;
    @Basic
    @Column(name = "privacy_status_id", nullable = true, length = 36)
    private String privacyStatusId;
    @ManyToOne
    @JoinColumn(name = "workplace_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Workplaces workplacesByWorkplaceId;
    @ManyToOne
    @JoinColumn(name = "privacy_status_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Privacies privaciesByPrivacyStatusId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Users usersByUserId;
}
