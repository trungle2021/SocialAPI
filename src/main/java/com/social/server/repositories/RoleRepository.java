package com.social.server.repositories;

import com.social.server.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Roles,String> {
    List<Roles> findByRoleType(String roleType);
    @Query("SELECT r FROM Roles r JOIN Accounts a ON a.roleId = r.id where a.id =:accountId")
    Roles findByAccountId(String accountId);
}
