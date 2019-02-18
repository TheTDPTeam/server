package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.semester.SemesterDTO;
import com.tdpteam.repo.entity.Course;
import com.tdpteam.service.interf.CourseService;
import com.tdpteam.service.interf.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/cms/semesters")
public class SemesterController {
    private SemesterService semesterService;
    private CourseService courseService;

    @Autowired
    public SemesterController(SemesterService semesterService,
                              CourseService courseService) {
        this.semesterService = semesterService;
        this.courseService = courseService;
    }

    @GetMapping
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("semesters", semesterService.getAllSemesters());
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
    public ModelAndView addSemester(@RequestParam(value = "courseId") Long courseId,
                                    @Valid @ModelAttribute("semester") SemesterDTO semesterDTO,
                                    BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("semester/addSemester");
        } else {
            semesterService.saveSemesterBySemesterDTO(semesterDTO, courseId);
            modelAndView.setViewName(semesterService.redirectToSemesterList());
        }
        return modelAndView;
    }

    @GetMapping("/changeActivation/{id}")
    public String changeActivation(@PathVariable(name = "id") Long id){
        semesterService.changeActivation(id);
        return semesterService.redirectToSemesterList();
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteCourse(@PathVariable(name = "id") Long id){
        semesterService.deleteSemester(id);
        return semesterService.redirectToSemesterList();
    }
}
