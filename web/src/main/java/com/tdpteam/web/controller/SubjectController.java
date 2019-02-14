package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.subject.SubjectDTO;
import com.tdpteam.service.interf.SemesterService;
import com.tdpteam.service.interf.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/cms")
public class SubjectController {
    private SubjectService subjectService;
    private SemesterService semesterService;

    @Autowired
    public SubjectController(SubjectService subjectService, SemesterService semesterService) {
        this.subjectService = subjectService;
        this.semesterService = semesterService;
    }

    @GetMapping(value = "/subjects")
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("subjects", subjectService.getAllSubjects());
        modelAndView.setViewName("subject/subjects");
        return modelAndView;
    }

    @GetMapping(value = "/subjects/add")
    public ModelAndView showAddSubjectView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("subject", new SubjectDTO());
        modelAndView.addObject("semesters", semesterService.getAllSemestersForSelection());
        modelAndView.setViewName("subject/addSubject");
        return modelAndView;
    }

    @PostMapping(value = "/subjects/add")
    public ModelAndView addSubject(@Valid @ModelAttribute("subject") SubjectDTO subjectDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("selectedSemesterId", subjectDTO.getSemesterId());
            modelAndView.addObject("semesters", semesterService.getAllSemestersForSelection());
            modelAndView.setViewName("subject/addSubject");
        } else {
            subjectService.saveSubject(subjectDTO);
            modelAndView.setViewName("redirect:/cms/subjects");
        }
        return modelAndView;
    }

    @GetMapping("/subjects/changeActivation/{id}")
    public String changeActivation(@PathVariable(name = "id") Long id){
        subjectService.changeActivation(id);
        return "redirect:/cms/subjects";
    }

    @GetMapping(value = "/subjects/delete/{id}")
    public String deleteCourse(@PathVariable(name = "id") Long id){
        subjectService.deleteSubject(id);
        return "redirect:/cms/subjects";
    }
}
