package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.course.CourseDTO;
import com.tdpteam.service.interf.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/cms/courses")
public class CourseController extends ExceptionController {
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("courses", courseService.getAllCourses());
        modelAndView.setViewName("course/courses");
        return modelAndView;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView getCourseById(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("course", courseService.getCourseDetails(id));
        modelAndView.setViewName("course/courseDetail");
        return modelAndView;
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddCourseView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("course", new CourseDTO());
        modelAndView.setViewName("course/addCourse");
        return modelAndView;
    }

    @PostMapping(value = "/add")
    public ModelAndView addCourse(@Valid @ModelAttribute("course") CourseDTO courseDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("course/addCourse");
        } else {
            courseService.saveCourseFromCourseDTO(courseDTO);
            modelAndView.setViewName(courseService.redirectToCourseList());
        }
        return modelAndView;
    }

    @GetMapping(value = "/edit/{id}")
    public ModelAndView getEditCourseView(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("courseId", id);
        modelAndView.addObject("course", courseService.getCourseDTO(id));
        modelAndView.setViewName("course/editCourse");
        return modelAndView;
    }

    @PostMapping(value = "/edit/{id}")
    public ModelAndView editCourse(@Valid @ModelAttribute("course") CourseDTO courseDTO,
                                   @PathVariable(name = "id") Long id,
                                   BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("course/editCourse");
        } else {
            courseService.updateCourse(id, courseDTO);
            modelAndView.setViewName(courseService.redirectToCourseList());
        }
        return modelAndView;
    }

    @GetMapping("/changeActivation/{id}")
    public String changeActivation(@PathVariable(name = "id") Long id){
        courseService.changeActivation(id);
        return courseService.redirectToCourseList();
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteCourse(@PathVariable(name = "id") Long id){
        courseService.deleteCourse(id);
        return courseService.redirectToCourseList();
    }
}