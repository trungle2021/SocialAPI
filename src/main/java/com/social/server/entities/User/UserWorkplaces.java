package com.social.server.entities.User;


import com.social.server.entities.Privacies;
import com.social.server.entities.Workplaces;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_workplaces", schema = "social")
@IdClass(UserWorkplacesPK.class)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
