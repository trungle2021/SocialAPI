package com.social.server.controllers;


import com.social.server.dtos.AuthResponse;
import com.social.server.dtos.LoginDTO;
import com.social.server.dtos.RegisterDTO;
import com.social.server.services.Auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(String refreshToken){
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }


}
