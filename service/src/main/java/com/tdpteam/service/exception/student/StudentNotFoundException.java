package com.tdpteam.service.exception.student;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
        super(StudentNotFoundException.class.getName() + " with id= " + id);
    }
}
