package com.tdpteam.service.interf;

import com.tdpteam.repo.api.response.UserDetailResponse;

public interface UserService {
    UserDetailResponse getUserDetailById(Long id);
}
