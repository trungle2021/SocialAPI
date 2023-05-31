package com.example.socialmediaproject.services;


import com.example.socialmediaproject.entities.Accounts;
import com.example.socialmediaproject.entities.RefreshTokens;

import java.util.List;

public interface RefreshTokenService {
    List<RefreshTokens> getAll();
    RefreshTokens getOneById(String id);
    RefreshTokens save(RefreshTokens refreshTokens);
    void delete(String id);
}
