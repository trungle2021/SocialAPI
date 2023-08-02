package com.social.server.services.implement;


import com.social.server.entities.User.Accounts;
import com.social.server.entities.Tokens;
import com.social.server.entities.Roles;
import com.social.server.securities.JWT.JwtService;
import com.social.server.services.*;
import com.social.server.dtos.AuthResponse;
import com.social.server.dtos.LoginDTO;
import com.social.server.dtos.RegisterDTO;
import com.social.server.utils.TokenType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final AccountService accountService;
    private final UserService userService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final String ACCESS_TOKEN = TokenType.ACCESS_TOKEN.toString();
    private final String REFRESH_TOKEN = TokenType.REFRESH_TOKEN.toString();

    @Override
    public AuthResponse login(LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        Accounts accounts = accountService.getOneByEmail(email);
        if(accounts != null){
            String accessToken = jwtService.generateToken(ACCESS_TOKEN,accounts);
            String refreshToken = jwtService.generateToken(REFRESH_TOKEN,accounts);
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
       return RegisterDTO.builder()
                .email(accounts.getEmail())
                .roleType(roleDTO.getRoleType())
                .build();

    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        Tokens token = refreshTokenService.findByRefreshToken(refreshToken);
       String newAccessToken = jwtService.generateToken(ACCESS_TOKEN,token.getAccountsByAccountId());
        return AuthResponse.builder()
                   .accessToken(newAccessToken)
                   .build();
    }
}


