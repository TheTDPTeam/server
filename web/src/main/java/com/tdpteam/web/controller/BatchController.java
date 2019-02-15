package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.batch.BatchDTO;
import com.tdpteam.service.interf.BatchService;
import com.tdpteam.service.interf.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/cms/batches")
public class BatchController {
    private BatchService batchService;
    private CourseService courseService;

    @Autowired
    public BatchController(BatchService batchService, CourseService courseService) {
        this.batchService = batchService;
        this.courseService = courseService;
    }

    @GetMapping
    public ModelAndView getAllBatches(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("batches", batchService.getAllBatchesForRendering());
        modelAndView.setViewName("batch/batches");
        return modelAndView;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView getBatchById(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("batch", batchService.getBatchById(id));
        modelAndView.setViewName("batch/batchDetails");
        return modelAndView;
    }

    @GetMapping(value = "/add")
    public ModelAndView getAddBatchView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("batch", new BatchDTO());
        modelAndView.addObject("courses", courseService.getAllCoursesForSelection());
        modelAndView.setViewName("batch/addBatch");
        return modelAndView;
    }

    @PostMapping(value = "/add")
    public ModelAndView addBatch(@Valid @ModelAttribute("batchDTO") BatchDTO batchDTO, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.addObject("batch", batchDTO);
            modelAndView.addObject("courses", courseService.getAllCoursesForSelection());
            modelAndView.setViewName("batch/addBatch");
        }else{
            batchService.createBatch(batchDTO);
            modelAndView.setViewName("redirect:/cms/batches");
        }
        return modelAndView;
    }
}
