package com.social.server.repositories.ElasticSearch;

import com.social.server.entities.User.Users;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserElasticsearchRepository extends ElasticsearchRepository<Users,String> {
}
