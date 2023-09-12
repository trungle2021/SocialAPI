package com.social.server.services;


import com.social.server.entities.RefreshTokens;

import java.util.List;

public interface RefreshTokenService {
    List<RefreshTokens> getAll();
    RefreshTokens getOneById(String id);
    RefreshTokens save(RefreshTokens refreshTokens);
    RefreshTokens findByRefreshToken(String token);
    void delete(String id);
}
