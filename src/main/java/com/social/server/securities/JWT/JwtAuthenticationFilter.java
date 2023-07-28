package com.social.server.securities.JWT;

import com.social.server.entities.Post.Posts;
import com.social.server.exceptions.SocialAppException;
import com.social.server.securities.CustomUserDetailServices.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomUserDetailService customUserDetailService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {
        final String userEmail;
        final String token;

        token = jwtService.getTokenFromHeader(request);






        if(token == null){
            filterChain.doFilter(request,response);
            return;
        }
        userEmail = jwtService.extractUsername(token);
        if(!userEmail.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailService.loadUserByUsername(userEmail);


            if(jwtService.isTokenValid(token,userDetails)){
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                       userDetails,userDetails.getPassword(),userDetails.getAuthorities()
               );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authToken);
            }else{
                throw new SocialAppException(HttpStatus.UNAUTHORIZED,"Token Expired");
            }
        }

        filterChain.doFilter(request,response);
    }
}
