package com.tdpteam.service.exception.teacher;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource Not Found")
public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(Long id) {
        super(TeacherNotFoundException.class.getName() + " with id= " + id);
    }
}
