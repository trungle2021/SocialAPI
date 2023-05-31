package com.example.socialmediaproject.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Accounts implements UserDetails {

    @Id
    @Column(name = "id",  length = 36)
    private String id;
    @Basic
    @Column(name = "email", nullable = true, length = 20)
    private String email;
    @Basic
    @Column(name = "password", nullable = true, length = 70)
    private String password;
    @OneToMany(mappedBy = "accountsByAccountId")
    private Collection<Users> usersById;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;
    @Basic
    @Column(name = "role_id", nullable = false, length = 36)
    private String roleId;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Roles rolesByRoleId;
    @OneToMany(mappedBy = "accountsByAccountId")
    private Collection<RefreshTokens> refreshTokensById;

    @PrePersist
    public void prePersist() {
        this.id = UUID.randomUUID().toString();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rolesByRoleId.getRoleType()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
