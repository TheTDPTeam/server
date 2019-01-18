package com.tdpteam.api.facade.impl;

import com.tdpteam.api.facade.AuthenticationFacade;
import com.tdpteam.repo.entity.user.Account;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    @Override
    public Account getCurrentUserPrincipal() {
        return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
