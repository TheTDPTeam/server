package com.tdpteam.service.exception.score;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class ScoreNotFoundException extends RuntimeException {
    public ScoreNotFoundException(Long id) {
        super(ScoreNotFoundException.class.getName() + " with id= " + id);
    }
}
