package com.social.server.services.User;


import com.social.server.dtos.UserDTO;
import com.social.server.entities.User.Accounts;
import com.social.server.entities.User.ElasticSearchModel.UserESModels;
import com.social.server.entities.User.Users;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.exceptions.SocialAppException;
import com.social.server.exceptions.UserNotFoundException;
import com.social.server.repositories.JPA.UserRepository;
import com.social.server.services.Account.AccountService;
import com.social.server.utils.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.social.server.utils.SD.ACCOUNT;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final AccountService accountService;
    private final UserRepository userRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    @Override
    public List<Users> getAll() {
        return null;
    }

    @Override
    public UserDTO getOneById(String id) {
        Users users =  userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return EntityMapper.mapToDto(users, UserDTO.class);
    }

    @Override
    @Transactional
    public UserESModels create(String accountId) {
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
        UserESModels usersCreated = EntityMapper.mapToDto(userRepository.save(user),UserESModels.class);
        //save to Elasticsearch
        checkIndexExists();
        elasticsearchOperations.save(usersCreated);
        return usersCreated;
    }

    @Override
    public Page<UserDTO> findByUsername(String username) {
        Criteria criteria = new Criteria("username").matchesAll(username);
        Query query = new CriteriaQuery(criteria);
        SearchHits<UserDTO> searchHits = elasticsearchOperations.search(query, UserDTO.class);

        return new PageImpl<>(searchHits.stream().map(SearchHit::getContent).toList());
    }

    private void checkIndexExists() {
        IndexOperations indexOperations = elasticsearchOperations.indexOps(UserDTO.class);
        if (!indexOperations.exists()) {
            indexOperations.create();
            indexOperations.putMapping(indexOperations.createMapping());
            indexOperations.refresh();
        }
    }

    @Override
    public UserDTO getOneByAccountId(String accountId) {
        Users person = userRepository.findByAccountId(accountId).orElseThrow(() -> new ResourceNotFoundException(ACCOUNT,"account_id",accountId));
        return EntityMapper.mapToDto(person, UserDTO.class);
    }

    @Override
    @Transactional
    public UserDTO update(String id) {
        return null;
    }

    @Override
    @Transactional
    public void delete(String id) {

    }


}
