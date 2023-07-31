package com.social.server.services.implement;


import com.social.server.entities.User.Users;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.exceptions.SocialAppException;
import com.social.server.exceptions.UserNotFoundException;
import com.social.server.repositories.UserRepository;
import com.social.server.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.social.server.utils.SD.ACCOUNT;
import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Users> getAll() {
        return null;
    }

    @Override
    public Users getOneById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional
    public Users create(String accountId) {
        if(accountId.isBlank()){
            throw new SocialAppException(HttpStatus.BAD_REQUEST,"AccountID is null");
        }
        Users user = Users.builder().accountId(accountId).build();
        user.setIsDeleted(false);

        //save to Elasticsearch
        elasticsearchOperations.save(user);

        return userRepository.save(user);
    }

    @Override
    public SearchHits<Users> findByName(String name) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(regexpQuery("title", ".*data.*"))
                .build();
        return elasticsearchOperations.search(searchQuery, Users.class, IndexCoordinates.of("users"));
    }

    @Override
    public Users getOneByAccountId(String accountId) {
        Users person = userRepository.findByAccountId(accountId);
        if(person == null){
            throw new ResourceNotFoundException(ACCOUNT,"account_id",accountId);
        }else{
            return person;
        }
    }

    @Override
    @Transactional
    public Users update(String id) {
        return null;
    }

    @Override
    @Transactional
    public void delete(String id) {

    }


}
