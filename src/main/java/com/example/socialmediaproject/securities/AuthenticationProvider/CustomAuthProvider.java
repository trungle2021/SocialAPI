package com.example.socialmediaproject.securities.AuthenticationProvider;

import com.example.socialmediaproject.exceptions.UserNotFoundException;
import com.example.socialmediaproject.securities.CustomUserDetailServices.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthProvider  implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService userDetailService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        boolean isPasswordCorrect;
        boolean isEmailCorrect;
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        if(email == null || password == null){
            throw new BadCredentialsException("Email and Password cannot be blank");
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(email);
        if(userDetails != null && userDetails.getPassword() != null && userDetails.getUsername() != null){
            isEmailCorrect = userDetails.getUsername().equals(email);
            isPasswordCorrect = passwordEncoder.matches(password, userDetails.getPassword());
            if(!isPasswordCorrect || !isEmailCorrect){
                throw new UserNotFoundException("Invalid Email or Password");
            }else{
                return new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
