package com.social.server.services.Token;

import com.social.server.entities.Tokens;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.exceptions.SocialAppException;
import com.social.server.repositories.JPA.RefreshTokenRepository;
import com.social.server.utils.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final String REFRESH_TOKEN = TokenType.REFRESH_TOKEN.toString();

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public List<Tokens> getAll() {
        return refreshTokenRepository.findAll();
    }

    @Override
    public Tokens getOneById(String id) {
        Tokens token = refreshTokenRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(REFRESH_TOKEN,"id",id));
        int compareTime = token.getExpiryTime().compareTo(new Date());
        if(compareTime >= 0){
            // >= 0 means this token expired

            throw new SocialAppException(HttpStatus.UNAUTHORIZED,"Refresh Token Expired");
        }else{
            return token;
        }
    }

    @Override
    public Tokens save(Tokens refreshTokens) {
        return refreshTokenRepository.save(refreshTokens);
    }

    @Override
    public Tokens findByRefreshToken(String refreshToken) {
        Tokens token =
                refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new ResourceNotFoundException(REFRESH_TOKEN,"token",refreshToken));


        Date now = new Date(System.currentTimeMillis());
        Date tokenExpiryTime;
        try {
            tokenExpiryTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(String.valueOf(token.getExpiryTime()));
        } catch (ParseException e) {
            throw new SocialAppException(HttpStatus.INTERNAL_SERVER_ERROR,"The Date Could Not Be Parsed.");
        }
        boolean isAfter = tokenExpiryTime.after(now);

        if(isAfter){
            return token;
        }else{
            throw new SocialAppException(HttpStatus.UNAUTHORIZED,"Refresh Token Expired");
        }
    }

    @Override
    public void delete(String id) {
        Tokens refreshToken = refreshTokenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(REFRESH_TOKEN,"id",id));
        refreshTokenRepository.delete(refreshToken);
    }
}
