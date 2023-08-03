package com.social.server.repositories.JPA;

import com.social.server.entities.User.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,String> {
    Optional<Accounts> findByEmail(String email);
}
