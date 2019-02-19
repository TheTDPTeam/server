package com.tdpteam.web.controller;

import com.tdpteam.service.interf.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cms")
public class NewsController {
    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public ModelAndView getNewsList(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("newsList", newsService.getNewsList());
        modelAndView.setViewName("news/newsList");
        return modelAndView;
    }

//    @GetMapping(value = "/add")
//    public ModelAndView getAddNewsView(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("batch", new BatchDTO());
//        modelAndView.addObject("courses", courseService.getAllCoursesForSelection());
//        modelAndView.setViewName("batch/addBatch");
//        return modelAndView;
//    }
//
//    @PostMapping(value = "/add")
//    public ModelAndView addBatch(@Valid @ModelAttribute("batch") BatchDTO batchDTO, BindingResult bindingResult){
//        ModelAndView modelAndView = new ModelAndView();
//        if(bindingResult.hasErrors()){
//            modelAndView.addObject("selectedCourseId", batchDTO.getCourseId());
//            modelAndView.addObject("courses", courseService.getAllCoursesForSelection());
//            modelAndView.setViewName("batch/addBatch");
//        }else{
//            batchService.createBatch(batchDTO);
//            modelAndView.setViewName(batchService.redirectToBatchList());
//        }
//        return modelAndView;
//    }
}
