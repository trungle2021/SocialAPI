package com.social.server.repositories.JpaRepositories;

import com.social.server.entities.User.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,String> {
    Accounts findByEmail(String email);
}
