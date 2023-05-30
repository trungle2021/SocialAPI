package com.example.socialmediaproject.services.implement;

import com.example.socialmediaproject.dtos.AccountDTO;
import com.example.socialmediaproject.dtos.RoleDTO;
import com.example.socialmediaproject.entities.Accounts;
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
import java.util.UUID;

import static com.example.socialmediaproject.utils.SD.ACCOUNT;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<AccountDTO> getAll() {
        return null;
    }

    @Override
    public AccountDTO getOneById(String id) {

        return null;
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        List<String> errorList = new ArrayList<>();
        RoleDTO roleDTO;
        Accounts accounts = new Accounts();

        if(accountDTO.getEmail().isEmpty()){
            errorList.add("Email is required");
        }else if(accountDTO.getPassword().isEmpty()){
            errorList.add("Password is required");
        }

        if(!errorList.isEmpty()){
            throw new SocialAppException(HttpStatus.BAD_REQUEST,errorList.toString());
        }
        if (accountDTO.getRoleId().isEmpty()) {
            roleDTO = roleService.getOneByRoleType("CUSTOMER");
            accounts.setId(roleDTO.getRoleId());
        }else{
            accounts.setId(accountDTO.getRoleId());
        }
        accounts.setEmail(accountDTO.getEmail());
        accounts.setIsDeleted(false);
        accounts.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        Accounts newAccount = accountRepository.save(accounts);
        return EntityMapper.mapToDto(newAccount,AccountDTO.class);
    }

    @Override
    public AccountDTO getOneByEmail(String email) {
        Accounts accounts = accountRepository.findByEmail(email);
        if(accounts == null){
            throw new ResourceNotFoundException("Email not exists");
        }
        return EntityMapper.mapToDto(accounts,AccountDTO.class);
    }

    @Override
    @Transactional
    public AccountDTO update(AccountDTO accountDTO) {
        String accountId = accountDTO.getAccountId();
        Accounts accounts = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException(ACCOUNT,"id",accountId));
        accounts.setEmail(accountDTO.getEmail() == null ? accounts.getEmail() : accountDTO.getEmail());
        accounts.setPassword(accountDTO.getPassword() == null ? accounts.getPassword() : passwordEncoder.encode(accountDTO.getPassword()));
        accounts.setId(accountDTO.getRoleId() == null ? accounts.getId() : accountDTO.getRoleId());
        return EntityMapper.mapToDto(accountRepository.save(accounts), AccountDTO.class);
    }

    @Override
    public void delete(String id) {
        Accounts accounts = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ACCOUNT,"id",id));
        accounts.setIsDeleted(true);
        accountRepository.save(accounts);
    }
}
