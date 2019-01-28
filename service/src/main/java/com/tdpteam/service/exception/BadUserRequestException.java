package com.tdpteam.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadUserRequestException extends RuntimeException {
    public BadUserRequestException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
