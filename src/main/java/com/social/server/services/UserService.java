package com.social.server.services;


import com.social.server.dtos.UserDTO;
import com.social.server.entities.User.Users;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<Users> getAll();
    UserDTO getOneById(String id);
    UserDTO create(String accountId);
    Page<UserDTO> findByUsername(String name);
    UserDTO getOneByAccountId(String accountId);
    UserDTO update(String id);
    void delete(String id);


}
