package com.example.socialmediaproject.services.implement;


import com.example.socialmediaproject.dtos.*;
import com.example.socialmediaproject.entities.Accounts;
import com.example.socialmediaproject.entities.Roles;
import com.example.socialmediaproject.exceptions.SocialAppException;
import com.example.socialmediaproject.securities.JWT.JwtService;
import com.example.socialmediaproject.services.*;
import com.example.socialmediaproject.utils.SD;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        Accounts accounts = accountService.getOneByEmail(email);
        if(accounts != null){
            String accessToken = jwtService.generateToken(SD.ACCESS_TOKEN,accounts);
            String refreshToken = jwtService.generateToken(SD.REFRESH_TOKEN,accounts);
            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }
        return null;
    }

    @Override
    @Transactional
    public RegisterDTO register(RegisterDTO registerDTO) {
        String email = registerDTO.getEmail();
        String password = registerDTO.getPassword();
        Roles roleDTO = roleService.getOneByRoleType(registerDTO.getRoleType());

        Accounts accounts = Accounts.builder()
                .email(email)
                .password(password)
                .rolesByRoleId(roleDTO).build();
        accounts = accountService.create(accounts);
        userService.create(accounts.getId());

        return null;
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        return null;
    }
}
