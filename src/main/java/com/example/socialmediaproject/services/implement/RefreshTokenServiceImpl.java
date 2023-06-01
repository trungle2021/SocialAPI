package com.example.socialmediaproject.services.implement;

import com.example.socialmediaproject.entities.Accounts;
import com.example.socialmediaproject.entities.RefreshTokens;
import com.example.socialmediaproject.exceptions.ResourceNotFoundException;
import com.example.socialmediaproject.exceptions.SocialAppException;
import com.example.socialmediaproject.repositories.AccountRepository;
import com.example.socialmediaproject.repositories.RefreshTokenRepository;
import com.example.socialmediaproject.services.AccountService;
import com.example.socialmediaproject.services.RefreshTokenService;
import com.example.socialmediaproject.utils.EntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.socialmediaproject.utils.SD.ACCOUNT;
import static com.example.socialmediaproject.utils.SD.REFRESH_TOKEN;


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
            throw new SocialAppException(HttpStatus.UNAUTHORIZED,"Token Expired");
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
        RefreshTokens token = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(()->new ResourceNotFoundException(REFRESH_TOKEN,"token",refreshToken));
        Date now = new Date(System.currentTimeMillis());
        Date formatter = null;
        try {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS").parse(String.valueOf(token.getExpiryTime()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        boolean isBefore = formatter.before(now);

        if(isBefore){
            // >= 0 means this token expired
            throw new SocialAppException(HttpStatus.UNAUTHORIZED,"Token Expired");
        }else{
            return token;
        }
    }

    @Override
    public void delete(String id) {
        RefreshTokens refreshToken = refreshTokenRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(REFRESH_TOKEN,"id",id));
        refreshTokenRepository.delete(refreshToken);
    }
}
