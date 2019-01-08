package com.tdpteam.repo.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountSetupDTO {

    @Email(message = "Please input a valid email")
    @NotEmpty(message = "Please input an email")
    private String email;

    @NotEmpty(message = "Please input your first name")
    @Pattern(regexp = "^[A-Za-z]*$", message = "Please input a valid first name")
    private String firstName;

    @NotEmpty(message = "Please input your last name")
    @Pattern(regexp = "^[A-Za-z]*$", message = "Please input a valid last name")
    private String lastName;
}
