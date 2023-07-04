package com.example.socialmediaproject.securities.JWT;

import com.example.socialmediaproject.entities.Accounts;
import com.example.socialmediaproject.entities.RefreshTokens;
import com.example.socialmediaproject.exceptions.SocialAppException;
import com.example.socialmediaproject.services.AccountService;
import com.example.socialmediaproject.services.RefreshTokenService;
import com.example.socialmediaproject.services.UserService;
import com.example.socialmediaproject.utils.SD;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtService {
    @Value("${app.jwt-secret}")
    public String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    public Long jwtExpiry;

    private final RefreshTokenService refreshTokenService;
    private final AccountService accountService;


    private Key getSignInKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String tokenType, UserDetails userDetails){
        return generateToken(tokenType, new HashMap<>(),userDetails);
    }

    public String generateToken(String tokenType, Map<String,Object> extraClaims, UserDetails userDetails){
        long timeExpired = 0;
        Date issuedAt = new Date(System.currentTimeMillis());
        String email = userDetails.getUsername();
        if(tokenType == null) {
            throw new SocialAppException(HttpStatus.INTERNAL_SERVER_ERROR, "Token Type Is Required");
        }else if(tokenType.equals(SD.ACCESS_TOKEN)){
            timeExpired = System.currentTimeMillis() + jwtExpiry;
       }else if(tokenType.equals(SD.REFRESH_TOKEN)){
            //expiry time of refresh token greater than 3 times access token
            //example access token expiry time is 1 day so refresh token is 3 days.
            timeExpired = System.currentTimeMillis() + jwtExpiry*3;
       }
        String token = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(email)
                .setIssuedAt(issuedAt)
                .setExpiration(new Date(timeExpired))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();


        if(tokenType.equals(SD.REFRESH_TOKEN)){
            Accounts account = accountService.getOneByEmail(email);
            RefreshTokens refreshTokens = RefreshTokens.builder()
                    .refreshToken(token)
                    .expiryTime(new Date(timeExpired))
                    .accountId(account.getId())
                    .createdAt(issuedAt)
                    .build();
            refreshTokenService.save(refreshTokens);
        }
        return token;
    }


    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        Claims claims = extractAllClaims(token);
       return claimsResolver.apply(claims);
    }


    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey())
                .build().parseClaimsJws(token).getBody();

    }

    public boolean isTokenValid(String token, UserDetails userDetails){
         String username = extractUsername(token);
         return username.equals(userDetails.getUsername()) || !isTokenExpired(token);
    }



    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public String getTokenFromHeader(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(Strings.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
