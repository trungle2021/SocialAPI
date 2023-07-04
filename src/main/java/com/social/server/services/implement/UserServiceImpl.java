package com.example.socialmediaproject.services.implement;


import com.example.socialmediaproject.entities.Users;
import com.example.socialmediaproject.exceptions.ResourceNotFoundException;
import com.example.socialmediaproject.exceptions.SocialAppException;
import com.example.socialmediaproject.exceptions.UserNotFoundException;
import com.example.socialmediaproject.repositories.UserRepository;
import com.example.socialmediaproject.services.UserService;
import com.example.socialmediaproject.utils.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.socialmediaproject.utils.SD.ACCOUNT;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

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
        return userRepository.save(user);
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
