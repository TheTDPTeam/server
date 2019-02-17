package com.tdpteam.web.controller;

import com.tdpteam.service.interf.AttendanceService;
import com.tdpteam.service.interf.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/cms/scores")
public class ScoreController {
    private ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping(value = "/{id}/update", params = {"theory","practical"})
    public String updateScore(@RequestParam("theory") Integer theory, @RequestParam("practical") Integer practical, @PathVariable("id") Long id, HttpServletRequest request){
        scoreService.updateScore(id, theory, practical);
        return "redirect:" + request.getHeader("Referer");
    }
}
