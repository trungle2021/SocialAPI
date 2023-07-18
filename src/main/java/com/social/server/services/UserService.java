package com.social.server.services;


import com.social.server.entities.User.Users;

import java.util.List;

public interface UserService {
    List<Users> getAll();
    Users getOneById(String id);
    Users create(String accountId);
    Users getOneByAccountId(String accountId);
    Users update(String id);
    void delete(String id);


}
