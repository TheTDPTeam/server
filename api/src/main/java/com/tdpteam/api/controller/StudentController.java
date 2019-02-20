package com.tdpteam.api.controller;

import com.tdpteam.api.facade.AuthenticationFacade;
import com.tdpteam.repo.api.response.ScoreListResponse;
import com.tdpteam.repo.entity.user.Account;
import com.tdpteam.service.interf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
    private AuthenticationFacade authenticationFacade;
    private StudentService studentService;

    @Autowired
    public StudentController(AuthenticationFacade authenticationFacade, StudentService studentService) {
        this.authenticationFacade = authenticationFacade;
        this.studentService = studentService;
    }

    @GetMapping("/my-score")
    public List<ScoreListResponse> getAllScoreOfStudent(){
        Account account = authenticationFacade.getCurrentUserPrincipal();
        Long studentId = account.getId();
        return studentService.getStudentScoreListById(studentId);
    }

    @GetMapping("/latestSemester")
    public int getLatestSemester(){
        Account account = authenticationFacade.getCurrentUserPrincipal();
        Long studentId = account.getId();
        return studentService.getLatestSemester(studentId);
    }
}
