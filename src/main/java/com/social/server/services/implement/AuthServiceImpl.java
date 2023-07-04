package com.social.server.services.implement;


import com.social.server.entities.Accounts;
import com.social.server.entities.RefreshTokens;
import com.social.server.entities.Roles;
import com.social.server.securities.JWT.JwtService;
import com.social.server.services.*;
import com.social.server.utils.SD;
import com.social.server.dtos.AuthResponse;
import com.social.server.dtos.LoginDTO;
import com.social.server.dtos.RegisterDTO;
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
        RefreshTokens token = refreshTokenService.findByRefreshToken(refreshToken);
       String newAccessToken = jwtService.generateToken(SD.ACCESS_TOKEN,token.getAccountsByAccountId());
        return AuthResponse.builder()
                   .accessToken(newAccessToken)
                   .build();
    }
}


