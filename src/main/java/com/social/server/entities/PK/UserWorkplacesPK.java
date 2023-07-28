package com.social.server.entities.PK;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserWorkplacesPK implements Serializable {
    @Column(name = "user_id", nullable = false, length = 36)
    @Id
    private String userId;
    @Column(name = "workplace_id", nullable = false, length = 36)
    @Id
    private String workplaceId;
}
