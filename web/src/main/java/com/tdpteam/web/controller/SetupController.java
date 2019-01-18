package com.tdpteam.web.controller;

import com.tdpteam.repo.dto.account.AccountSetupDTO;
import com.tdpteam.repo.entity.user.UserDetail;
import com.tdpteam.service.helper.RoleType;
import com.tdpteam.repo.entity.user.Account;
import com.tdpteam.repo.repository.RoleRepository;
import com.tdpteam.service.interf.AccountService;
import com.tdpteam.service.interf.ConfigService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SetupController {
    private final AccountService accountService;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;
    private final ConfigService configService;

    @Autowired
    public SetupController(AccountService accountService,
                           ModelMapper modelMapper,
                           RoleRepository roleRepository,
                           ConfigService configService) {
        this.accountService = accountService;
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.configService = configService;
    }

    @RequestMapping(value = {"/setup"}, method = RequestMethod.GET)
    public ModelAndView showSetup() {
        ModelAndView modelAndView = new ModelAndView();
        if (configService.isAdminConfigured()) {
            modelAndView.setViewName("redirect:/login");
        } else {
            AccountSetupDTO accountSetupDTO = new AccountSetupDTO();
            modelAndView.addObject("account", accountSetupDTO);
            modelAndView.setViewName("setup");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/setup"}, method = RequestMethod.POST)
    public ModelAndView setup(@Valid @ModelAttribute("account") AccountSetupDTO accountSetupDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("setup");
        } else {
            Account adminAccount = modelMapper.map(accountSetupDTO, Account.class);
            adminAccount = accountService.updateUserDetail(accountSetupDTO, adminAccount);
            adminAccount.setRole(roleRepository.findByRole(String.valueOf(RoleType.ADMIN)));
            Account result = accountService.saveAccount(adminAccount);
            configService.setAdminConfigured(true);
            modelAndView.setViewName(result == null ? "setup" : "redirect:/login");
        }
        return modelAndView;
    }
}
