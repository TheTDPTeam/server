package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.semester.SemesterSelectionItemDTO;
import com.tdpteam.repo.dto.subject.SubjectDTO;
import com.tdpteam.repo.dto.subject.SubjectListItemDTO;
import com.tdpteam.service.interf.SemesterService;
import com.tdpteam.service.interf.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cms")
public class SubjectController {
    private SubjectService subjectService;
    private SemesterService semesterService;
    private ModelMapper modelMapper;

    @Autowired
    public SubjectController(SubjectService subjectService, SemesterService semesterService, ModelMapper modelMapper) {
        this.subjectService = subjectService;
        this.semesterService = semesterService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/subjects")
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView();
        List<SubjectListItemDTO> subjectListItemDTOList = subjectService.getAllSubjects();
        modelAndView.addObject("subjects", subjectListItemDTOList);
        modelAndView.setViewName("subject/subjects");
        return modelAndView;
    }

    @GetMapping(value = "/subjects/add")
    public ModelAndView showAddSubjectView() {
        ModelAndView modelAndView = new ModelAndView();
        SubjectDTO subjectDTO = new SubjectDTO();
        List<SemesterSelectionItemDTO> semesterSelectionItemDTO = semesterService.getAllSemestersForSelection();
        modelAndView.addObject("subject", subjectDTO);
        modelAndView.addObject("semesters", semesterSelectionItemDTO);
        modelAndView.setViewName("subject/addSubject");
        return modelAndView;
    }

    @PostMapping(value = "/subjects/add")
    public ModelAndView addSubject(@Valid @ModelAttribute("subject") SubjectDTO subjectDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            List<SemesterSelectionItemDTO> semesterSelectionItemDTO = semesterService.getAllSemestersForSelection();
            modelAndView.addObject("selectedSemesterId", subjectDTO.getSemesterId());
            modelAndView.addObject("semesters", semesterSelectionItemDTO);
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
