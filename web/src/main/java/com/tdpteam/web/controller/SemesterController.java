package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.course.CourseSelectionItemDTO;
import com.tdpteam.repo.dto.semester.SemesterDTO;
import com.tdpteam.repo.dto.semester.SemesterListItemDTO;
import com.tdpteam.repo.entity.Course;
import com.tdpteam.repo.entity.Semester;
import com.tdpteam.service.interf.CourseService;
import com.tdpteam.service.interf.SemesterService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cms")
public class SemesterController {
    private static final Logger logger =
            LoggerFactory.getLogger(SemesterController.class);
    private SemesterService semesterService;
    private CourseService courseService;
    private ModelMapper modelMapper;

    @Autowired
    public SemesterController(SemesterService semesterService, CourseService courseService, ModelMapper modelMapper) {
        this.semesterService = semesterService;
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/semesters")
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView();
        List<SemesterListItemDTO> semesterListItemDTOList = semesterService.getAllSemesters();
        modelAndView.addObject("semesters", semesterListItemDTOList);
        modelAndView.setViewName("semester/semesters");
        return modelAndView;
    }

    @GetMapping(value = "/semesters/add")
    public ModelAndView showAddSemesterView(@RequestParam(value = "courseId", required = false) Long courseId) {
        ModelAndView modelAndView = new ModelAndView();
        SemesterDTO semesterDTO = new SemesterDTO();
        List<CourseSelectionItemDTO> courseSelectionItemDTO = courseService.getAllCoursesForSelection();
        if(courseId != null){
            modelAndView.addObject("courseId", courseId);
            Course course = courseService.findById(courseId);
            modelAndView.addObject("newSemesterName", "Semester " + (course.getSemesters().size() + 1));
        }
        modelAndView.addObject("semester", semesterDTO);
        modelAndView.addObject("courses", courseSelectionItemDTO);
        modelAndView.setViewName("semester/addSemester");
        return modelAndView;
    }

    @PostMapping(value = "/semesters/add", params = "courseId")
    public ModelAndView addCourse(@RequestParam(value = "courseId") Long courseId, @Valid @ModelAttribute("semester") SemesterDTO semesterDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("semester/addSemester");
        } else {
            Semester semester = modelMapper.map(semesterDTO, Semester.class);
            semesterService.saveSemester(semester, courseId);
            modelAndView.setViewName("redirect:/cms/semesters");
        }
        return modelAndView;
    }

    @GetMapping("/semesters/changeActivation/{id}")
    public String changeActivation(@PathVariable(name = "id") Long id){
        semesterService.changeActivation(id);
        return "redirect:/cms/semesters";
    }

    @GetMapping(value = "/semesters/delete/{id}")
    public String deleteCourse(@PathVariable(name = "id") Long id){
        semesterService.deleteSemester(id);
        return "redirect:/cms/semesters";
    }
}
