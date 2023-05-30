package com.example.socialmediaproject.services;


import com.example.socialmediaproject.dtos.AuthResponse;
import com.example.socialmediaproject.dtos.LoginDTO;
import com.example.socialmediaproject.dtos.RegisterDTO;

public interface AuthService {
    AuthResponse login(LoginDTO loginDTO);
    RegisterDTO register(RegisterDTO registerDTO);
}
