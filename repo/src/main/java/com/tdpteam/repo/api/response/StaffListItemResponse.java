package com.tdpteam.repo.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StaffListItemResponse {
    private String fullName;
    private String email;
    private String phoneNumber;
}
