package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.account.AccountListItemDTO;
import com.tdpteam.repo.entity.user.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Account findAccountByEmail(String email);
    Account saveAccount(Account user);
    String generateAccountCreationMailContent(String firstName, String email, String password);
    Page<AccountListItemDTO> getPaginatedAccountList(Pageable pageable);
    void changeAccountActivation(Long id);
    void deleteAccount(Long id);
}