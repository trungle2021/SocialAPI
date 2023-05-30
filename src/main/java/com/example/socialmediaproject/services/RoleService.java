package com.example.socialmediaproject.services;


import com.example.socialmediaproject.dtos.RoleDTO;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    List<RoleDTO> getAll();

    RoleDTO getOneByRoleType(String roleType);
    RoleDTO getOneById(String id);
    RoleDTO getOneByAccountId(String accountId);
    RoleDTO create(RoleDTO roleDTO);
    RoleDTO update(String id);
    void delete(String id);
}
