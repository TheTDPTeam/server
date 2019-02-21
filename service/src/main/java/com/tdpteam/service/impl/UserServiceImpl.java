package com.tdpteam.service.impl;

import com.tdpteam.repo.api.request.UpdateDetailRequest;
import com.tdpteam.repo.api.response.UserDetailResponse;
import com.tdpteam.repo.entity.user.Account;
import com.tdpteam.repo.entity.user.UserDetail;
import com.tdpteam.repo.repository.AccountRepository;
import com.tdpteam.service.interf.AccountService;
import com.tdpteam.service.interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private AccountRepository accountRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(AccountRepository accountRepository,
                           BCryptPasswordEncoder encoder) {
        this.accountRepository = accountRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetailResponse getUserDetailById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(!optionalAccount.isPresent()) return null;
        Account account = optionalAccount.get();
        return UserDetailResponse
                .builder()
                .email(account.getEmail())
                .phone(account.getUserDetail().getPhone())
                .firstName(account.getUserDetail().getFirstName())
                .lastName(account.getUserDetail().getLastName())
                .build();
    }

    @Override
    public void changePassword(Account account, String password) {
        account.setPassword(encoder.encode(password));
        accountRepository.save(account);
    }

    @Override
    public ResponseEntity<UserDetailResponse> updateUserDetail(Account account, UpdateDetailRequest data) {
        UserDetail userDetail = account.getUserDetail();
        userDetail.setLastName(data.getLastName());
        userDetail.setFirstName(data.getFirstName());
        userDetail.setPhone(data.getPhone());
        account.setUserDetail(userDetail);
        Account updatedAccount = accountRepository.save(account);
        return new ResponseEntity<>(UserDetailResponse
                .builder()
                .email(updatedAccount.getEmail())
                .phone(updatedAccount.getUserDetail().getPhone())
                .firstName(updatedAccount.getUserDetail().getFirstName())
                .lastName(updatedAccount.getUserDetail().getLastName())
                .build(), HttpStatus.OK);
    }
}
