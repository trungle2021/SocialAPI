package com.social.server.entities.User;

import com.social.server.entities.MaritalStatus;
import com.social.server.entities.Privacies;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "user_marital_status", schema = "social")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMaritalStatus {
    @Id
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;
    @Basic
    @Column(name = "user_partner_id", nullable = true, length = 36)
    private String userPartnerId;
    @Basic
    @Column(name = "is_accepted", nullable = true)
    private Boolean isAccepted;
    @Basic
    @Column(name = "from_time", nullable = true)
    private Instant fromTime;
    @Basic
    @Column(name = "marital_status_id", nullable = true, length = 36)
    private String maritalStatusId;
    @Basic
    @Column(name = "privacy_status_id", nullable = true, length = 36)
    private String privacyStatusId;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users usersByUserId;
    @ManyToOne
    @JoinColumn(name = "user_partner_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Users usersByUserPartnerId;
    @ManyToOne
    @JoinColumn(name = "marital_status_id", referencedColumnName = "id", insertable = false, updatable = false)
    private MaritalStatus maritalStatusByMaritalStatusId;
    @ManyToOne
    @JoinColumn(name = "privacy_status_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Privacies privaciesByPrivacyStatusId;
}
