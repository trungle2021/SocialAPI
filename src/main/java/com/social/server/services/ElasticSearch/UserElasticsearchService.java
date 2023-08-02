package com.social.server.services.ElasticSearch;

import com.social.server.entities.User.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserElasticsearchService {
    Page<Users> findByUsername(String username, Pageable pageable);
}
