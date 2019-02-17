package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.account.AccountCreationDTO;
import com.tdpteam.repo.dto.account.AccountListItemDTO;
import com.tdpteam.repo.entity.user.Student;
import com.tdpteam.repo.entity.user.Teacher;
import com.tdpteam.repo.entity.user.UserDetail;
import com.tdpteam.repo.repository.RoleRepository;
import com.tdpteam.repo.repository.StudentRepository;
import com.tdpteam.repo.repository.TeacherRepository;
import com.tdpteam.service.exception.user.UserNotFoundException;
import com.tdpteam.service.helper.Constants;
import com.tdpteam.repo.entity.user.Account;
import com.tdpteam.repo.repository.AccountRepository;
import com.tdpteam.service.helper.RoleType;
import com.tdpteam.service.interf.AccountService;
import com.tdpteam.service.interf.MailService;
import com.tdpteam.service.interf.PasswordService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private PasswordService passwordService;
    private MailService mailService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ModelMapper modelMapper;
    @Qualifier("getAccountCreationMailContentTemplate")
    private String emailTemplate;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              RoleRepository roleRepository,
                              StudentRepository studentRepository,
                              TeacherRepository teacherRepository,
                              PasswordService passwordService,
                              MailService mailService,
                              BCryptPasswordEncoder bCryptPasswordEncoder,
                              ModelMapper modelMapper,
                              String emailTemplate) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.passwordService = passwordService;
        this.mailService = mailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.emailTemplate = emailTemplate;
    }

    @Override
    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Account findAccountById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if(!account.isPresent()){
            throw new UserNotFoundException(Constants.USER_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        return account.get();
    }

    @Override
    public Account updateUserDetail(Object accountUpdateDTO, Account account) {
        UserDetail userDetail = new UserDetail(account);
        modelMapper.map(accountUpdateDTO, userDetail);
        account.setUserDetail(userDetail);
        return account;
    }

    @Override
    public Account saveAccount(Account user) {
        String generatedPassword = passwordService.generatePassword();
        user.setPassword(bCryptPasswordEncoder.encode(generatedPassword));
        String emailContent = generateAccountCreationMailContent(user.getUserDetail().getFirstName(), user.getEmail(), generatedPassword);
        mailService.sendMail(user.getEmail(), Constants.ACCOUNT_CREATION_EMAIL_SUBJECT, emailContent);
        user.setActivated(true);
        return accountRepository.save(user);
    }

    @Override
    public String generateAccountCreationMailContent(String firstName, String email, String generatedPassword) {
        return String.format(Objects.requireNonNull(emailTemplate), firstName, email, generatedPassword);
    }

    @Override
    public Page<AccountListItemDTO> getPaginatedAccountList(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);
        return accounts.map(account -> modelMapper.map(account, AccountListItemDTO.class));
    }

    @Override
    public void changeActivation(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setActivated(!account.isActivated());
            accountRepository.save(account);
        }
    }

    @Override
    public void deleteAccount(Long id) {
        try{
            accountRepository.deleteById(id);
        }catch (Exception ignored){}
    }

    @Override
    public void createAccount(AccountCreationDTO accountCreationDTO) {
        Account userAccount = modelMapper.map(accountCreationDTO, Account.class);
        userAccount = updateUserDetail(accountCreationDTO, userAccount);
        String roleName = accountCreationDTO.getRole();
        userAccount.setRole(roleRepository.findByRole(roleName));
        Account savedAccount = saveAccount(userAccount);
        if(savedAccount == null) return;
        switch (RoleType.valueOf(roleName)){
            case STUDENT:
                studentRepository.save(new Student(savedAccount));
                break;
            case TEACHER:
                teacherRepository.save(new Teacher(savedAccount));
                break;
        }
    }

    @Override
    public Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            String email = ((UserDetails) principal).getUsername();
            Account account = accountRepository.findByEmail(email);
            return account.getId();
        }
        return null;
    }
}
