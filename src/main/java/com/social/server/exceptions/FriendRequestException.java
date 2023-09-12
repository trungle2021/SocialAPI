package com.social.server.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class FriendRequestException extends RuntimeException{
    public FriendRequestException(String message) {
        super(message);
    }

    public FriendRequestException() {
        super("FRIEND REQUEST ALREADY SENT");
    }
}
