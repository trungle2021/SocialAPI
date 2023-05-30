package com.example.socialmediaproject.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserSchoolsPK implements Serializable {
    @Column(name = "user_id", nullable = false, length = 36)
    @Id
    private String userId;
    @Column(name = "school_id", nullable = false, length = 36)
    @Id
    private String schoolId;
}
