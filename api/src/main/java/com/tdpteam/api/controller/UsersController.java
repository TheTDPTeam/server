package com.tdpteam.api.controller;

import com.tdpteam.api.facade.AuthenticationFacade;
import com.tdpteam.repo.api.response.UserDetailResponse;
import com.tdpteam.repo.entity.user.Account;
import com.tdpteam.service.exception.user.BadUserRequestException;
import com.tdpteam.service.helper.Constants;
import com.tdpteam.service.interf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private UserService userService;
    private AuthenticationFacade authenticationFacade;

    @Autowired
    public UsersController(UserService userService, AuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponse> getUserDetails(@PathVariable String id){
        try{
            Long longId = Long.parseLong(id);
            UserDetailResponse userDetailResponse = userService.getUserDetailById(longId);
            return new ResponseEntity<>(userDetailResponse, HttpStatus.ACCEPTED);
        }catch (NumberFormatException ex){
            throw new BadUserRequestException(Constants.USER_BAD_REQUEST_EXCEPTION_MESSAGE);
        }
    }

    @GetMapping("/myDetails")
    public ResponseEntity<UserDetailResponse> getMyUserDetails(){
        Account account = authenticationFacade.getCurrentUserPrincipal();
        UserDetailResponse userDetailResponse = userService.getUserDetailById(account.getId());
        return new ResponseEntity<>(userDetailResponse, HttpStatus.ACCEPTED);
    }
}
