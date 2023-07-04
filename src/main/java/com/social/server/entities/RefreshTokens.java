package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens", schema = "social")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshTokens {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;
    @Basic
    @Column(name = "refresh_token", nullable = false, length = 255)
    private String refreshToken;
    @Basic
    @Column(name = "expiry_time", nullable = false)
    private Date expiryTime;
    @Basic
    @Column(name = "created_at", nullable = false)
    private Date createdAt;
    @Basic
    @Column(name = "account_id", nullable = false, length = 36)
    private String accountId;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    private Accounts accountsByAccountId;
    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }

}
