package com.tdpteam.service.exception.bClass;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class BClassNotFoundException extends RuntimeException {
    public BClassNotFoundException(Long id) {
        super(BClassNotFoundException.class.getName() + " with id= " + id);
    }
}
