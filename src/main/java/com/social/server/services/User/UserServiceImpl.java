package com.social.server.services.User;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.ExistsRequest;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.social.server.configs.ElasticSearch.Indices;
import com.social.server.dtos.User.UserDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.social.server.configs.ElasticSearch.Indices.USER_INDEX;
import static com.social.server.utils.SD.ACCOUNT;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final AccountService accountService;
    private final UserRepository userRepository;
    private final ElasticsearchClient esClient;
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
        String documentId = null;
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
        boolean isIndexExists = checkIndexExists();
        try{
            if(isIndexExists){
                 IndexResponse indexResponse = esClient.index(i->
                            i.index(USER_INDEX)
                                    .id(usersCreated.getId())
                                    .document(usersCreated));
                  documentId = indexResponse.id();
            }else{
                    esClient.indices().create(c -> c
                            .index(Indices.USER_INDEX)
                    );
            }
        }catch (IOException e){
            throw new SocialAppException(HttpStatus.INTERNAL_SERVER_ERROR,"Cannot insert user into ElasticSearch");
        }
        usersCreated.setDocumentId(documentId);
        return usersCreated;
    }

    @Override
    public List<UserESModels> findByUsername(String username) throws IOException {
        SearchResponse<UserESModels> searchResponse = esClient.search(
                s -> s.index(USER_INDEX)
                       .query(q -> q.match(
                                        t->t.field("username")
                                        .query(username))), UserESModels.class);


         List<Hit<UserESModels>> hits = searchResponse.hits().hits();
        return hits.stream()
                .map(Hit::source)
                .toList();
    }

    private boolean checkIndexExists()  {
        try{
            BooleanResponse result = esClient
                    .indices()
                    .exists(ExistsRequest.of(e -> e.index(USER_INDEX)));
            return result.value();
        }catch (IOException ex){
            throw new SocialAppException(HttpStatus.INTERNAL_SERVER_ERROR,"Index not exists");
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
