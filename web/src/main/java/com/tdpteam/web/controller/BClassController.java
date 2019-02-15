package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.bClass.BClassDTO;
import com.tdpteam.service.interf.BClassService;
import com.tdpteam.service.interf.SubjectService;
import com.tdpteam.service.interf.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/cms/classes")
public class BClassController {
    private BClassService bClassService;
    private SubjectService subjectService;
    private TeacherService teacherService;

    @Autowired
    public BClassController(BClassService bClassService, SubjectService subjectService, TeacherService teacherService) {
        this.bClassService = bClassService;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
    }

    @GetMapping
    public ModelAndView getAllClasses(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bClasses", bClassService.getAllBClassesForRendering());
        modelAndView.setViewName("bClass/bClasses");
        return modelAndView;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView getClassById(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bClass", bClassService.getBClassById(id));
        modelAndView.setViewName("bClass/bClassDetails");
        return modelAndView;
    }

    @GetMapping(value = "/add")
    public ModelAndView getAddBatchView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bClass", new BClassDTO());
        modelAndView.addObject("subjects", subjectService.getAllSubjectsForSelection());
        modelAndView.addObject("students", teacherService.getAllTeachersForSelection());
        modelAndView.setViewName("bClass/addBClass");
        return modelAndView;
    }

    @PostMapping(value = "/add")
    public ModelAndView addBClass(@Valid @ModelAttribute("bClassDTO") BClassDTO bClassDTO, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.addObject("subjects", subjectService.getAllSubjectsForSelection());
            modelAndView.addObject("students", teacherService.getAllTeachersForSelection());
            modelAndView.setViewName("bClass/addBClass");
        }else{
            bClassService.createBClass(bClassDTO);
            modelAndView.setViewName("redirect:/cms/classes");
        }
        return modelAndView;
    }
}
