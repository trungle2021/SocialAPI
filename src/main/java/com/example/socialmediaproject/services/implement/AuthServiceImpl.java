package com.example.socialmediaproject.services.implement;


import com.example.socialmediaproject.dtos.AccountDTO;
import com.example.socialmediaproject.dtos.AuthResponse;
import com.example.socialmediaproject.dtos.LoginDTO;
import com.example.socialmediaproject.dtos.RegisterDTO;
import com.example.socialmediaproject.securities.JWT.JwtService;
import com.example.socialmediaproject.services.AccountService;
import com.example.socialmediaproject.services.AuthService;
import com.example.socialmediaproject.services.RoleService;
import com.example.socialmediaproject.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final UserService userService;
    private final RoleService roleService;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        AccountDTO accounts = accountService.getOneByEmail(email);
        if(accounts != null){
            String token = jwtService.generateToken((UserDetails) accounts);
            return AuthResponse.builder()
                    .accessToken(token).build();
        }
        return null;
    }

    @Override
    @Transactional
    public RegisterDTO register(RegisterDTO registerDTO) {
        String email = registerDTO.getEmail();
        String password = registerDTO.getPassword();
        String roleId = roleService.getOneByRoleType(registerDTO.getRoleName()).getRoleId();

        AccountDTO accountDTO = AccountDTO.builder()
                .email(email)
                .password(password)
                .roleId(roleId).build();
        accountDTO = accountService.create(accountDTO);
        userService.create(accountDTO.getAccountId());

        return null;
    }
}
