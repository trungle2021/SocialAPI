package com.example.socialmediaproject.repositories;

import com.example.socialmediaproject.entities.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokens,String> {
}
