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
public class AccountCreationDTO {
    @Email(message = "Please input a valid email")
    @NotEmpty(message = "Please input an email")
    private String email;

    @NotEmpty(message = "Please input user's first name")
    @Pattern(regexp = "^[A-Za-z]*$", message = "Please input a valid first name")
    private String firstName;

    @NotEmpty(message = "Please input user's last name")
    @Pattern(regexp = "^[A-Za-z]*$", message = "Please input a valid last name")
    private String lastName;

    @NotEmpty(message = "Please select user role")
    private String role;

    @NotEmpty(message = "Please input user's phone number")
    @Pattern(regexp = "^[0-9]*$", message = "Please input a valid phone number")
    private String phone;
}
