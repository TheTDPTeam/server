package com.tdpteam.web.controller;

import com.tdpteam.service.interf.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import static com.tdpteam.web.helper.HttpHelper.getGoBackRedirect;

@Controller
@RequestMapping("/cms/attendances")
public class AttendanceController {
    private AttendanceService attendanceService;

    @Autowired
    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping(value = "/{id}/update", params = "value")
    public String updateAttendance(@RequestParam("value") String value, @PathVariable("id") Long id, HttpServletRequest request){
        attendanceService.updateAttendanceStatus(id, value);
        return getGoBackRedirect(request);
    }
}
