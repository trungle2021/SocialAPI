package com.social.server.services.implement;


import com.social.server.entities.User.Users;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.exceptions.SocialAppException;
import com.social.server.exceptions.UserNotFoundException;
import com.social.server.configs.ElasticSearch.repos.ESUserRepository;
import com.social.server.repositories.UserRepository;
import com.social.server.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.social.server.utils.SD.ACCOUNT;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ESUserRepository esUserRepository;

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
        esUserRepository.save(user);

        return userRepository.save(user);
    }

    @Override
    public SearchPage<Users> findByUsername(String username) {
        Pageable pageable = PageRequest.of(0, 10);
        SearchPage<Users> result = esUserRepository.findByUsername(username,pageable);
        return result;
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
