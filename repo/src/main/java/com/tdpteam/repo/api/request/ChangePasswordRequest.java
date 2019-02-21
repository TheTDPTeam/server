package com.tdpteam.repo.api.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String password;
    private String retypePassword;
}
