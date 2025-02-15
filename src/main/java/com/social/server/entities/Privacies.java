package com.social.server.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Privacies {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "privacy_type", nullable = true, length = 45)
    private String privacyType;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
