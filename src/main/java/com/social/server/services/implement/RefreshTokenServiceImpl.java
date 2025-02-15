package com.social.server.services.implement;

import com.social.server.entities.RefreshTokens;
import com.social.server.exceptions.ResourceNotFoundException;
import com.social.server.exceptions.SocialAppException;
import com.social.server.repositories.RefreshTokenRepository;
import com.social.server.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.social.server.utils.SD.REFRESH_TOKEN;


@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {


    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public List<RefreshTokens> getAll() {
        return refreshTokenRepository.findAll();
    }

    @Override
    public RefreshTokens getOneById(String id) {
        RefreshTokens token = refreshTokenRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(REFRESH_TOKEN,"id",id));
        int compareTime = token.getExpiryTime().compareTo(new Date());
        if(compareTime >= 0){
            // >= 0 means this token expired
            throw new SocialAppException(HttpStatus.UNAUTHORIZED,"Refresh Token Expired");
        }else{
            return token;
        }
    }

    @Override
    public RefreshTokens save(RefreshTokens refreshTokens) {
        return refreshTokenRepository.save(refreshTokens);
    }

    @Override
    public RefreshTokens findByRefreshToken(String refreshToken) {
        RefreshTokens token =
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
        RefreshTokens refreshToken = refreshTokenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(REFRESH_TOKEN,"id",id));
        refreshTokenRepository.delete(refreshToken);
    }
}
