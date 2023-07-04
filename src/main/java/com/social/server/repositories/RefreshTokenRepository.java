package com.social.server.repositories;

import com.social.server.entities.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokens,String> {
    @Query("select t from RefreshTokens t where t.refreshToken = :token")
    Optional<RefreshTokens> findByRefreshToken(String token);
}
