package com.tdpteam.api.facade;

import com.tdpteam.repo.entity.user.Account;

public interface AuthenticationFacade {
    Account getCurrentUserPrincipal();
}
