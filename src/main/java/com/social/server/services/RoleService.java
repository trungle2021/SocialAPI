package com.example.socialmediaproject.services;


import com.example.socialmediaproject.entities.Roles;

import java.util.List;

public interface RoleService {
    List<Roles> getAll();

    Roles getOneByRoleType(String roleType);
    Roles getOneById(String id);
    Roles getOneByAccountId(String accountId);
    Roles create(Roles role);
    Roles update(String id);
    void delete(String id);
}
