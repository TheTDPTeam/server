package com.tdpteam.api.config;

import com.tdpteam.repo.entity.user.Account;
import com.tdpteam.service.interf.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private AccountService accountService;

    @Autowired
    public CustomUserDetailsService(AccountService accountService) {
        this.accountService = accountService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails account = this.accountService.findAccountByEmail(username);
        if(account == null){
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }
        return account;
    }
}