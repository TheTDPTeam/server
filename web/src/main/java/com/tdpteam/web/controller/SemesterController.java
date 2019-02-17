package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.semester.SemesterDTO;
import com.tdpteam.repo.dto.semester.SemesterListItemDTO;
import com.tdpteam.repo.entity.Course;
import com.tdpteam.repo.entity.Semester;
import com.tdpteam.service.interf.CourseService;
import com.tdpteam.service.interf.SemesterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cms/semesters")
public class SemesterController {
    private SemesterService semesterService;
    private CourseService courseService;
    private ModelMapper modelMapper;

    @Autowired
    public SemesterController(SemesterService semesterService, CourseService courseService, ModelMapper modelMapper) {
        this.semesterService = semesterService;
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView();
        List<SemesterListItemDTO> semesterListItemDTOList = semesterService.getAllSemesters();
        modelAndView.addObject("semesters", semesterListItemDTOList);
        modelAndView.setViewName("semester/semesters");
        return modelAndView;
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddSemesterView(@RequestParam(value = "courseId", required = false) Long courseId) {
        ModelAndView modelAndView = new ModelAndView();
        SemesterDTO semesterDTO = new SemesterDTO();
        if(courseId != null){
            modelAndView.addObject("courseId", courseId);
            Course course = courseService.findById(courseId);
            modelAndView.addObject("newSemesterName", "Semester " + (course.getSemesters().size() + 1));
        }
        modelAndView.addObject("semester", semesterDTO);
        modelAndView.addObject("courses", courseService.getAllCoursesForSelection());
        modelAndView.setViewName("semester/addSemester");
        return modelAndView;
    }

    @PostMapping(value = "/add", params = "courseId")
    public ModelAndView addSemester(@RequestParam(value = "courseId") Long courseId, @Valid @ModelAttribute("semester") SemesterDTO semesterDTO, BindingResult bindingResult) {
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

    @GetMapping("/changeActivation/{id}")
    public String changeActivation(@PathVariable(name = "id") Long id){
        semesterService.changeActivation(id);
        return "redirect:/cms/semesters";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteCourse(@PathVariable(name = "id") Long id){
        semesterService.deleteSemester(id);
        return "redirect:/cms/semesters";
    }
}
