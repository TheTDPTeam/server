package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.account.AccountCreationDTO;
import com.tdpteam.repo.dto.account.AccountListItemDTO;
import com.tdpteam.repo.entity.user.Account;
import com.tdpteam.repo.repository.RoleRepository;
import com.tdpteam.service.interf.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AccountController {
    private final AccountService accountService;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Autowired
    public AccountController(AccountService accountService, ModelMapper modelMapper, RoleRepository roleRepository) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/accounts")
    public ModelAndView seeAllAccount(Pageable pageable){
        ModelAndView modelAndView = new ModelAndView();
        Page<AccountListItemDTO> accounts = accountService.getPaginatedAccountList(pageable);
        modelAndView.addObject("accounts",accounts);
        modelAndView.setViewName("account/accountList");
        return modelAndView;
    }

    @GetMapping("/accounts/add")
    public ModelAndView getAddAccountView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("account/addAccount");
        AccountCreationDTO account = new AccountCreationDTO();
        modelAndView.addObject("account", account);
        return modelAndView;
    }

    @PostMapping("/accounts/add")
    public ModelAndView addUserAccount(@Valid @ModelAttribute("account") AccountCreationDTO accountCreationDTO, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("account/addAccount");
        } else {
            Account userAccount = modelMapper.map(accountCreationDTO, Account.class);
            userAccount = accountService.updateUserDetail(accountCreationDTO, userAccount);
            userAccount.setRole(roleRepository.findByRole(String.valueOf(accountCreationDTO.getRole())));
            accountService.saveAccount(userAccount);
            modelAndView.setViewName("redirect:/accounts");
        }
        return modelAndView;
    }

    @GetMapping("/accounts/delete/{id}")
    public String deleteAccount(@PathVariable(name = "id") Long id){
        accountService.deleteAccount(id);
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/changeActivation/{id}")
    public String changeActivation(@PathVariable(name = "id") Long id){
        accountService.changeAccountActivation(id);
        return "redirect:/accounts";
    }
}
