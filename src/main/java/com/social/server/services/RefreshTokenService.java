package com.social.server.services;


import com.social.server.entities.Tokens;

import java.util.List;

public interface RefreshTokenService {
    List<Tokens> getAll();
    Tokens getOneById(String id);
    Tokens save(Tokens refreshTokens);
    Tokens findByRefreshToken(String token);
    void delete(String id);
}
