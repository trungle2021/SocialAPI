package com.social.server.repositories;

import com.social.server.entities.User.Users;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserESRepository extends ElasticsearchRepository<Users,String> {
}
