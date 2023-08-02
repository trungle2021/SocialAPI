package com.social.server.repositories.ElasticsearchRepositories;

import com.social.server.entities.User.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserESRepository extends ElasticsearchRepository<Users,String> {
    SearchPage<Users> findByUsername(String username, Pageable pageable);
}
