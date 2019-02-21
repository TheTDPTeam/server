package com.tdpteam.repo.api.request;

import lombok.Data;

@Data
public class UpdateDetailRequest {
    private String firstName;
    private String lastName;
    private String phone;
}
