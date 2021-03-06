package com.tdpteam.web.controller;

import com.tdpteam.service.interf.BatchService;
import com.tdpteam.service.interf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/cms/students")
public class StudentController {
    private StudentService studentService;
    private BatchService batchService;

    @Autowired
    public StudentController(StudentService studentService, BatchService batchService) {
        this.studentService = studentService;
        this.batchService = batchService;
    }

    @GetMapping
    public ModelAndView getAllStudents() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("students", studentService.getStudentListDTO());
        modelAndView.setViewName("student/students");
        return modelAndView;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView getStudentById(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("student", studentService.findStudentById(id));
        modelAndView.addObject("batches", batchService.getAllBatchesForSelection());
        modelAndView.setViewName("student/studentDetails");
        return modelAndView;
    }

    @PostMapping(value = "/{id}/setBatch")
    public ModelAndView setBatchForStudent(@PathVariable(name = "id") Long id, @ModelAttribute("batchId") Long batchId) {
        ModelAndView modelAndView = new ModelAndView();
        studentService.changeBatch(id, batchId);
        modelAndView.setViewName("redirect:/cms/students");
        return modelAndView;
    }
}
