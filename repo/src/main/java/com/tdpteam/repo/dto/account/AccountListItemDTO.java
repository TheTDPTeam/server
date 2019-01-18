package com.tdpteam.repo.dto.account;

import com.tdpteam.repo.entity.user.Role;
import com.tdpteam.repo.entity.user.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountListItemDTO {
    private Long id;

    private String email;

    private UserDetail userDetail;

    private Date createdAt;

    private boolean isActivated;

    private Role role;
}
