package com.example.socialmediaproject.services;


import com.example.socialmediaproject.entities.Accounts;

import java.util.List;

public interface AccountService {
    List<Accounts> getAll();
    Accounts getOneById(String id);
    Accounts create(Accounts accounts);
    Accounts getOneByEmail(String email);
    Accounts update(Accounts accounts);
    void delete(String id);
}
