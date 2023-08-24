package com.social.server.services.Account;


import com.social.server.entities.User.Accounts;

import java.util.List;

public interface AccountService {
    List<Accounts> getAll();
    Accounts getOneById(String id);
    Accounts create(Accounts accounts);
    Accounts getOneByEmail(String email);
    Accounts update(Accounts accounts);
    void delete(String id);
}
