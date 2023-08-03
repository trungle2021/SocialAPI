package com.social.server.services.implement;


import com.social.server.entities.User.Accounts;
import com.social.server.entities.User.Users;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.exceptions.SocialAppException;
import com.social.server.exceptions.UserNotFoundException;
import com.social.server.repositories.JPA.UserRepository;
import com.social.server.services.AccountService;
import com.social.server.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.social.server.utils.SD.ACCOUNT;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final AccountService accountService;
    private final UserRepository userRepository;
//    private final UserElasticsearchService userElasticsearchService;
//    private final ElasticsearchOperations elasticsearchOperations;
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
        Accounts accounts = accountService.getOneById(accountId);
        String email = accounts.getEmail();
        Users user = Users.builder()
                .accountId(accountId)
                .username(email)
                .isDeleted(false)
                .build();
       Users usersCreated = userRepository.save(user);
        //save to Elasticsearch
//        elasticsearchOperations.save(usersCreated);
        return usersCreated;
    }

    @Override
    public Page<Users> findByUsername(String username) {
//        Pageable pageable = PageRequest.of(0, 10);
//       return userElasticsearchService.findByUsername(username,pageable);
        return null;
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
