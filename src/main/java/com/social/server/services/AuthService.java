package com.social.server.services;


import com.social.server.dtos.AuthResponse;
import com.social.server.dtos.LoginDTO;
import com.social.server.dtos.RegisterDTO;

public interface AuthService {
    AuthResponse login(LoginDTO loginDTO);
    RegisterDTO register(RegisterDTO registerDTO);
    AuthResponse refreshToken(String refreshToken);
}
