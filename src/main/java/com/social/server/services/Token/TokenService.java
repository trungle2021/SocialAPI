package com.social.server.services.Token;


import com.social.server.entities.Tokens;

import java.util.List;

public interface TokenService {
    List<Tokens> getAll();
    Tokens getOneById(String id);
    Tokens save(Tokens refreshTokens);
    Tokens findByRefreshToken(String token);
    void delete(String id);
}
