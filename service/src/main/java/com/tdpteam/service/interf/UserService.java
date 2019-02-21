package com.tdpteam.service.interf;

import com.tdpteam.repo.api.request.UpdateDetailRequest;
import com.tdpteam.repo.api.response.UserDetailResponse;
import com.tdpteam.repo.entity.user.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

public interface UserService {
    UserDetailResponse getUserDetailById(Long id);

    @Async
    void changePassword(Account account, String password);

    ResponseEntity<UserDetailResponse> updateUserDetail(Account account, UpdateDetailRequest data);
}
