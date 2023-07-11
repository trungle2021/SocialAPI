package com.social.server.securities.CustomUserDetailServices;


import com.social.server.dtos.AuthUserDTO;
import com.social.server.entities.User.Accounts;
import com.social.server.securities.CustomUserDetails.CustomUserDetails;
import com.social.server.services.AccountService;
import com.social.server.services.UserService;
import com.social.server.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final AccountService accountService;
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Accounts account = accountService.getOneByEmail(email);
        if(account == null){
            throw new UsernameNotFoundException("Email not exists");
        }else{
            String accountId = account.getId();
            AuthUserDTO authUserDTO = new AuthUserDTO();
            String roleType = String.valueOf(roleService.getOneByAccountId(accountId));
            String userId = userService.getOneByAccountId(accountId).getId();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(roleType));
            authUserDTO.setEmail(account.getEmail());
            authUserDTO.setPassword(account.getPassword());
            authUserDTO.setAuthorities(authorities);
            authUserDTO.setUserID(userId);
            return new CustomUserDetails(authUserDTO);
        }
    }
}
