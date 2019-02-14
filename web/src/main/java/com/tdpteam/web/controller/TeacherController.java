package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.teacher.TeacherListItemDTO;
import com.tdpteam.service.interf.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/cms")
public class TeacherController {
    private TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping(value = "/teachers")
    public ModelAndView getAllTeachers(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teachers", teacherService.getTeacherListDTO());
        modelAndView.setViewName("teacher/teachers");
        return modelAndView;
    }

    @GetMapping(value = "/teachers/{id}")
    public ModelAndView getTeacherById(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teacher", teacherService.findTeacherById(id));
        modelAndView.setViewName("teacher/teacherDetails");
        return modelAndView;
    }
}
