package com.tdpteam.repo.api.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailResponse {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
}
