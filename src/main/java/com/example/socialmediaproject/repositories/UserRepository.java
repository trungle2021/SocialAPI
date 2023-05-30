package com.example.socialmediaproject.repositories;

import com.example.socialmediaproject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<Users,String> {
    @Query("Select u from Accounts a join Users u on a.id = u.accountId where a.id = :accountId")
    Users findByAccountId(String accountId);
}
