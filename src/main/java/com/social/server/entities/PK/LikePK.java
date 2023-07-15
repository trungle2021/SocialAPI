package com.social.server.entities.PK;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LikePK implements Serializable {
    @Column(name = "id", nullable = false, length = 36)
    @Id
    private String id;
    @Basic
    @Id
    @Column(name = "user_id", nullable = true, length = 36)
    private String userId;
    @Column(name = "post_base_id", nullable = false, length = 36)
    @Id
    private String postBaseId;

}
