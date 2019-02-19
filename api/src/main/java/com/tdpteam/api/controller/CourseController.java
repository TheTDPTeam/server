package com.tdpteam.api.controller;

import com.tdpteam.repo.api.response.CourseApiItemResponse;
import com.tdpteam.service.interf.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public List<CourseApiItemResponse> getCourses(){
        return courseService.getAllCourseInfo();
    }
}
