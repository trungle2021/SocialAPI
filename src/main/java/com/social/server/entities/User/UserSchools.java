package com.social.server.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_schools", schema = "social")
@IdClass(UserSchoolsPK.class)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSchools {

    @Id
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;
    @Id
    @Column(name = "school_id", nullable = false, length = 36)
    private String schoolId;
    @Basic
    @Column(name = "privacy_status_id", nullable = true, length = 36)
    private String privacyStatusId;
    @ManyToOne
    @JoinColumn(name = "school_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Schools schoolsBySchoolId;
    @ManyToOne
    @JoinColumn(name = "privacy_status_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Privacies privaciesByPrivacyStatusId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Users usersByUserId;
}
