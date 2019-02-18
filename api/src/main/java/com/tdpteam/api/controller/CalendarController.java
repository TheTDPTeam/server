package com.tdpteam.api.controller;

import com.tdpteam.api.facade.AuthenticationFacade;
import com.tdpteam.repo.api.response.StudentCalendarResponse;
import com.tdpteam.repo.entity.user.Account;
import com.tdpteam.service.interf.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CalendarController {
    private AttendanceService attendanceService;
    private AuthenticationFacade authenticationFacade;

    @Autowired
    public CalendarController(AttendanceService attendanceService, AuthenticationFacade authenticationFacade) {
        this.attendanceService = attendanceService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/my-calendar")
    public StudentCalendarResponse getMyCalendar(){
        Account account = authenticationFacade.getCurrentUserPrincipal();
        Long studentId = account.getId();
        return attendanceService.getAttendancesByStudentId(studentId);
    }
}
