package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.bClass.BClassDTO;
import com.tdpteam.service.interf.*;
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
    private StudentService studentService;
    private AttendanceService attendanceService;
    private ScoreService scoreService;

    @Autowired
    public BClassController(BClassService bClassService,
                            SubjectService subjectService,
                            TeacherService teacherService,
                            StudentService studentService,
                            AttendanceService attendanceService,
                            ScoreService scoreService) {
        this.bClassService = bClassService;
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.attendanceService = attendanceService;
        this.scoreService = scoreService;
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
        modelAndView.addObject("bClass", bClassService.getBClassDetail(id));
        modelAndView.setViewName("bClass/bClassDetail");
        return modelAndView;
    }

    @GetMapping(value = "/add")
    public ModelAndView getAddBatchView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("bClass", new BClassDTO());
        getObjectsForAddingBClasses(modelAndView);
        return modelAndView;
    }

    private void getObjectsForAddingBClasses(ModelAndView modelAndView) {
        modelAndView.addObject("subjects", subjectService.getAllSubjectsForSelection());
        modelAndView.addObject("students", studentService.getAvailableStudentsForJoiningClass());
        modelAndView.addObject("teachers", teacherService.getAllTeachersForSelection());
        modelAndView.setViewName("bClass/addBClass");
    }

    @PostMapping(value = "/add")
    public ModelAndView addBClass(@Valid @ModelAttribute("bClass") BClassDTO bClassDTO, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            getObjectsForAddingBClasses(modelAndView);
        }else{
            bClassService.createBClass(bClassDTO);
            modelAndView.setViewName(bClassService.redirectToClassList());
        }
        return modelAndView;
    }

    @GetMapping("/checkAttendance/{id}")
    public ModelAndView getAttendanceView(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("attendances", attendanceService.getAttendancesByClassId(id));
        modelAndView.setViewName("bClass/attendances");
        return modelAndView;
    }

    @GetMapping("/checkScore/{id}")
    public ModelAndView getScoreView(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("scores", scoreService.getScoreListOfBClass(id));
        modelAndView.setViewName("bClass/scores");
        return modelAndView;
    }

    @GetMapping("/changeActivation/{id}")
    public String changeActivation(@PathVariable(name = "id") Long id){
        bClassService.changeActivation(id);
        return bClassService.redirectToClassList();
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteCourse(@PathVariable(name = "id") Long id){
        bClassService.deleteCourse(id);
        return bClassService.redirectToClassList();
    }

    @ModelAttribute("multiCheckboxAllValues")
    public String[] getMultiCheckboxAllValues() {
        return new String[] {
                "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday", "Sunday"
        };
    }
}
