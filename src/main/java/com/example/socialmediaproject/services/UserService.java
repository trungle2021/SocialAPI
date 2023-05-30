package com.example.socialmediaproject.services;


import com.example.socialmediaproject.dtos.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAll();
    UserDTO getOneById(String id);

    UserDTO create(String accountId);

    UserDTO getOneByAccountId(String accountId);
    UserDTO update(String id);
    void delete(String id);
}
