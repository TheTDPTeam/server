package com.tdpteam.web.controller;

import com.tdpteam.service.helper.ExceptionLogGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class ExceptionController {
    private static final Logger logger =
            LoggerFactory.getLogger(CourseController.class);

    @ExceptionHandler
    public ModelAndView handleNotFoundException(HttpServletRequest request, Exception ex){
        logger.error(ExceptionLogGenerator.getRequestedUrlMessage(request.getRequestURL().toString()));
        logger.error(ExceptionLogGenerator.getExceptionName(ex));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("resourceNotFound");
        return modelAndView;
    }
}
