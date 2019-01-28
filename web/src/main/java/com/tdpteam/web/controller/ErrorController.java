package com.tdpteam.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {
    @GetMapping(value = "/notfound")
    public ModelAndView returnNotFoundPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("resourceNotFound");
        return modelAndView;
    }
}
