package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Entity
@Data
public class Workplaces {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "company", nullable = true, length = 45)
    private String company;
    @Basic
    @Column(name = "position", nullable = true, length = 20)
    private String position;
    @Basic
    @Column(name = "address", nullable = true, length = 45)
    private String address;
    @Basic
    @Column(name = "description", nullable = true, length = 45)
    private String description;
    @OneToMany(mappedBy = "workplacesByWorkplaceId")
    private Collection<UserWorkplaces> userWorkplacesById;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
