package com.social.server.repositories.JpaRepositories;

import com.social.server.entities.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<Tokens,String> {
    @Query("select t from Tokens t where t.token = :token")
    Optional<Tokens> findByRefreshToken(String token);
}
