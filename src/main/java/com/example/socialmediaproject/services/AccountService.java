package com.example.socialmediaproject.services;


import com.example.socialmediaproject.dtos.AccountDTO;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAll();
    AccountDTO getOneById(String id);
    AccountDTO create(AccountDTO accountDTO);
    AccountDTO getOneByEmail(String email);
    AccountDTO update(AccountDTO accountDTO);
    void delete(String id);
}
