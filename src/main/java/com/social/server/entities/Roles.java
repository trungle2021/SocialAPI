package com.social.server.entities;

import com.social.server.entities.User.Accounts;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Roles {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "role_type", nullable = true, length = 20)
    private String roleType;
    @OneToMany(mappedBy = "rolesByRoleId")
    private Collection<Accounts> accountsById;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
