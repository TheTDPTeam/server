package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.course.CourseDTO;
import com.tdpteam.repo.dto.course.CourseEditDTO;
import com.tdpteam.repo.dto.course.CourseListItemDTO;
import com.tdpteam.repo.entity.Course;
import com.tdpteam.service.exception.CourseNotFoundException;
import com.tdpteam.service.interf.CourseService;
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
public class CourseController {
    private CourseService courseService;
    private ModelMapper modelMapper;

    @Autowired
    public CourseController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }


    @GetMapping(value = "/courses")
    public ModelAndView getAllCourses(){
        ModelAndView modelAndView = new ModelAndView();
        List<CourseListItemDTO> courseListItemDTOList = courseService.getAllCourses();
        modelAndView.addObject("courses", courseListItemDTOList);
        modelAndView.setViewName("course/courses");
        return modelAndView;
    }

    @GetMapping(value = "/courses/add")
    public ModelAndView showAddCourseView(){
        ModelAndView modelAndView = new ModelAndView();
        CourseDTO courseDTO = new CourseDTO();
        modelAndView.addObject("course", courseDTO);
        modelAndView.setViewName("course/addCourse");
        return modelAndView;
    }

    @PostMapping(value = "/courses/add")
    public ModelAndView addCourse(@Valid @ModelAttribute("course") CourseDTO courseDTO, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("course/addCourse");
        } else{
            Course course = modelMapper.map(courseDTO, Course.class);
            courseService.saveCourse(course);
            modelAndView.setViewName("redirect:/cms/courses");
        }
        return modelAndView;
    }

    @GetMapping(value = "/courses/edit/{id}")
    public ModelAndView getEditCourseView(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        try {
            CourseEditDTO courseEditDTO = modelMapper.map(courseService.findById(id), CourseEditDTO.class);
            modelAndView.addObject("course", courseEditDTO);
            modelAndView.setViewName("courses/editCourse");
        }catch (CourseNotFoundException ex){
            modelAndView.setViewName("/error");
        }
        return modelAndView;
    }

    @PostMapping(value = "/courses/edit")
    public ModelAndView editCourse(@Valid @ModelAttribute("course") CourseEditDTO courseDTO, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("course/editCourse");
        } else{
            Course course = modelMapper.map(courseDTO, Course.class);
            courseService.saveCourse(course);
            modelAndView.setViewName("redirect:/cms/courses");
        }
        return modelAndView;
    }
}
