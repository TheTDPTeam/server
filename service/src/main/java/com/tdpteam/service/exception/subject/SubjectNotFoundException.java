package com.tdpteam.service.exception.subject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class SubjectNotFoundException extends RuntimeException {
    public SubjectNotFoundException(Long id) {
        super(SubjectNotFoundException.class.getName() + " with id= " + id);
    }
}
