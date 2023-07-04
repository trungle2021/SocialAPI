package com.example.socialmediaproject.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Data
public class SocialAppException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public SocialAppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
