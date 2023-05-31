package com.example.socialmediaproject.controllers;


import com.example.socialmediaproject.dtos.AuthResponse;
import com.example.socialmediaproject.dtos.LoginDTO;
import com.example.socialmediaproject.dtos.RegisterDTO;
import com.example.socialmediaproject.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDTO> register(@RequestBody RegisterDTO registerDTO){
        return ResponseEntity.ok(authService.register(registerDTO));
    }
}
