package com.social.server.services.User;


import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.social.server.dtos.User.UserDTO;
import com.social.server.entities.User.ElasticSearchModel.UserESModels;
import com.social.server.entities.User.Users;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<Users> getAll();
    UserDTO getOneById(String id);
    UserESModels create(String accountId);
    List<UserESModels> findByUsername(String name) throws IOException;
    UserDTO getOneByAccountId(String accountId);
    UserDTO update(String id);
    void delete(String id);


}
