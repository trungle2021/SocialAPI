package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "marital_status", schema = "social")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaritalStatus {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "type", nullable = true, length = 15)
    private String type;
    @OneToMany(mappedBy = "maritalStatusByMaritalStatusId")
    private Collection<UserMaritalStatus> userMaritalStatusesById;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
