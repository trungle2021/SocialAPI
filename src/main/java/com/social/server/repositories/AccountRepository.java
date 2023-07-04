package com.example.socialmediaproject.repositories;

import com.example.socialmediaproject.entities.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,String> {
    Accounts findByEmail(String email);
}
