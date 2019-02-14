package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.course.CourseDTO;
import com.tdpteam.repo.dto.course.CourseDetailDTO;
import com.tdpteam.repo.dto.course.CourseListItemDTO;
import com.tdpteam.repo.entity.Course;
import com.tdpteam.service.helper.ExceptionLogGenerator;
import com.tdpteam.service.interf.CourseService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cms")
public class CourseController {
    private static final Logger logger =
            LoggerFactory.getLogger(CourseController.class);
    private CourseService courseService;
    private ModelMapper modelMapper;

    @Autowired
    public CourseController(CourseService courseService, ModelMapper modelMapper) {
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }


    @GetMapping(value = "/courses")
    public ModelAndView getAllCourses() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("courses", courseService.getAllCourses());
        modelAndView.setViewName("course/courses");
        return modelAndView;
    }

    @GetMapping(value = "/courses/{id}")
    public ModelAndView getCourseById(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("course", courseService.getCourseDetails(id));
        modelAndView.setViewName("course/courseDetail");
        return modelAndView;
    }

    @GetMapping(value = "/courses/add")
    public ModelAndView showAddCourseView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("course", new CourseDTO());
        modelAndView.setViewName("course/addCourse");
        return modelAndView;
    }

    @PostMapping(value = "/courses/add")
    public ModelAndView addCourse(@Valid @ModelAttribute("course") CourseDTO courseDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("course/addCourse");
        } else {
            Course course = modelMapper.map(courseDTO, Course.class);
            courseService.saveCourse(course);
            modelAndView.setViewName("redirect:/cms/courses");
        }
        return modelAndView;
    }

    @GetMapping(value = "/courses/edit/{id}")
    public ModelAndView getEditCourseView(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Course course = courseService.findById(id);
        CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
        courseDTO.setIsActivated(course.isActivated());
        modelAndView.addObject("courseId", id);
        modelAndView.addObject("course", courseDTO);
        modelAndView.setViewName("course/editCourse");
        return modelAndView;
    }

    @PostMapping(value = "/courses/edit/{id}")
    public ModelAndView editCourse(@Valid @ModelAttribute("course") CourseDTO courseDTO,
                                   @PathVariable(name = "id") Long id,
                                   BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("course/editCourse");
        } else {
            courseService.updateCourse(id, courseDTO);
            modelAndView.setViewName("redirect:/cms/courses");
        }
        return modelAndView;
    }

    @GetMapping("/courses/changeActivation/{id}")
    public String changeActivation(@PathVariable(name = "id") Long id){
        courseService.changeActivation(id);
        return "redirect:/cms/courses";
    }

    @GetMapping(value = "/courses/delete/{id}")
    public String deleteCourse(@PathVariable(name = "id") Long id){
        courseService.deleteCourse(id);
        return "redirect:/cms/courses";
    }

    @ExceptionHandler
    public ModelAndView handleCourseNotFoundException(HttpServletRequest request, Exception ex){
        logger.error(ExceptionLogGenerator.getRequestedUrlMessage(request.getRequestURL().toString()));
        logger.error(ExceptionLogGenerator.getExceptionName(ex));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("resourceNotFound");
        return modelAndView;
    }
}
