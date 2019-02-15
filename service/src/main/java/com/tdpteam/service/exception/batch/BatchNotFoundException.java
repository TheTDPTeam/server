package com.tdpteam.service.exception.batch;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class BatchNotFoundException extends RuntimeException {
    public BatchNotFoundException(Long id) {
        super(BatchNotFoundException.class.getName() + " with id= " + id);
    }
}
