package com.social.server.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(value = HttpStatus.CONFLICT)
@Data
public class DuplicateRecordException extends RuntimeException{
    public DuplicateRecordException(String resource, String field, String value) {
        super(resource + " already existed with " + field + " : " + value);
    }

    public DuplicateRecordException(String message) {
        super(message);
    }

    public DuplicateRecordException() {

    }
}
