package com.tdpteam.api.controller;

import com.tdpteam.repo.api.response.StaffListItemResponse;
import com.tdpteam.service.interf.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StaffController {
    private AccountService accountService;

    @Autowired
    public StaffController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/staffs")
    public List<StaffListItemResponse> getAllStaffs(){
        return accountService.getAllStaffs();
    }
}
