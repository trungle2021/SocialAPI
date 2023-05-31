package com.example.socialmediaproject.services.implement;

import com.example.socialmediaproject.entities.Accounts;
import com.example.socialmediaproject.entities.Roles;
import com.example.socialmediaproject.exceptions.SocialAppException;
import com.example.socialmediaproject.exceptions.ResourceNotFoundException;
import com.example.socialmediaproject.repositories.AccountRepository;
import com.example.socialmediaproject.services.AccountService;
import com.example.socialmediaproject.services.RoleService;
import com.example.socialmediaproject.utils.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.socialmediaproject.utils.SD.ACCOUNT;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Accounts> getAll() {
        return null;
    }

    @Override
    public Accounts getOneById(String id) {

        return null;
    }

    @Override
    public Accounts create(Accounts accounts) {
        List<String> errorList = new ArrayList<>();
        String email = accounts.getEmail();
        String password = accounts.getPassword();
        String roleId = accounts.getRolesByRoleId().getId();

        if(email.isEmpty()){
            errorList.add("Email is required");
        }else if(password.isEmpty()){
            errorList.add("Password is required");
        }
        if(!errorList.isEmpty()){
            throw new SocialAppException(HttpStatus.BAD_REQUEST,errorList.toString());
        }
        Accounts newAccount = new Accounts();
        newAccount.setEmail(accounts.getEmail());
        newAccount.setIsDeleted(false);
        newAccount.setPassword(passwordEncoder.encode(accounts.getPassword()));
        newAccount.setRoleId(roleId);
        return accountRepository.save(newAccount);
    }

    @Override
    public Accounts getOneByEmail(String email) {
        Accounts accounts = accountRepository.findByEmail(email);
        if(accounts == null){
            throw new ResourceNotFoundException("Email not exists");
        }
        return EntityMapper.mapToDto(accounts,Accounts.class);
    }

    @Override
    @Transactional
    public Accounts update(Accounts account) {
        String accountId = account.getId();
        Accounts currentAccount = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException(ACCOUNT,"id",accountId));
        currentAccount.setEmail(account.getEmail() == null ? currentAccount.getEmail() : account.getEmail());
        currentAccount.setPassword(account.getPassword() == null ? currentAccount.getPassword() : passwordEncoder.encode(account.getPassword()));
        currentAccount.setRoleId(account.getRoleId() == null ? currentAccount.getId() : account.getId());
        return accountRepository.save(currentAccount);
    }

    @Override
    public void delete(String id) {
        Accounts accounts = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ACCOUNT,"id",id));
        accounts.setIsDeleted(true);
        accountRepository.save(accounts);
    }
}
