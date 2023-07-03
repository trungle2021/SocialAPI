package com.example.socialmediaproject.services;


import com.example.socialmediaproject.entities.Users;

import java.util.List;

public interface UserService {
    List<Users> getAll();
    Users getOneById(String id);

    Users create(String accountId);

    Users getOneByAccountId(String accountId);
    Users update(String id);
    void delete(String id);


}
