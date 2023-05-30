package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.UUID;

@Entity
@Data
public class Schools {

    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "school_name", nullable = true, length = 45)
    private String schoolName;
    @Basic
    @Column(name = "address", nullable = true, length = 45)
    private String address;
    @Basic
    @Column(name = "description", nullable = true, length = 50)
    private String description;
    @OneToMany(mappedBy = "schoolsBySchoolId")
    private Collection<UserSchools> userSchoolsById;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
}
