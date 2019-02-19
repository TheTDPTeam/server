package com.tdpteam.repo.repository;

import com.tdpteam.repo.entity.user.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {
    Account findByEmail(String email);
    List<Account> findAllByRole_Role(String role);
}
