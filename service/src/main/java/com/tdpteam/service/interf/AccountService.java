package com.tdpteam.service.interf;

import com.tdpteam.repo.api.response.StaffListItemResponse;
import com.tdpteam.repo.dto.account.AccountCreationDTO;
import com.tdpteam.repo.dto.account.AccountListItemDTO;
import com.tdpteam.repo.dto.account.AccountSetupDTO;
import com.tdpteam.repo.entity.user.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService extends ActivationService{
    Account findAccountByEmail(String email);
    Account findAccountById(Long id);
    Account updateUserDetail(Object accountUpdateDTO, Account account);
    Account saveAccount(Account user);
    String generateAccountCreationMailContent(String firstName, String email, String password);
    Page<AccountListItemDTO> getPaginatedAccountList(Pageable pageable);
    void deleteAccount(Long id);
    void createAccount(AccountCreationDTO accountCreationDTO);

    Long getCurrentUserId();

    Account createAdminAccount(AccountSetupDTO accountSetupDTO);

    List<StaffListItemResponse> getAllStaffs();
}
