package com.example.socialmediaproject.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Data
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resource,String field,String value) {
        super(resource + " not found with " + field + " : " + value);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
