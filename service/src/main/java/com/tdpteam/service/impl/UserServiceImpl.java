package com.tdpteam.service.impl;

import com.tdpteam.repo.api.response.UserDetailResponse;
import com.tdpteam.repo.entity.user.Account;
import com.tdpteam.service.interf.AccountService;
import com.tdpteam.service.interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private AccountService accountService;

    @Autowired
    public UserServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserDetailResponse getUserDetailById(Long id) {
        Account account = accountService.findAccountById(id);
        return UserDetailResponse
                .builder()
                .email(account.getEmail())
                .phone(account.getUserDetail().getPhone())
                .fullName(account.getUserDetail().getFullName())
                .build();
    }
}
