package com.social.server.dtos;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class AuthUserDTO {
    private String userID;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
}
