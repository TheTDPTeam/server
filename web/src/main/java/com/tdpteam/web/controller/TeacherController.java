package com.tdpteam.web.controller;

import com.tdpteam.service.interf.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/cms/teachers")
public class TeacherController {
    private TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ModelAndView getAllTeachers(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teachers", teacherService.getTeacherListDTO());
        modelAndView.setViewName("teacher/teachers");
        return modelAndView;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView getTeacherById(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("teacher", teacherService.findTeacherById(id));
        modelAndView.setViewName("teacher/teacherDetails");
        return modelAndView;
    }
}
