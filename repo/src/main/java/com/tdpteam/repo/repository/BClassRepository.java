package com.tdpteam.repo.repository;

import com.tdpteam.repo.entity.BClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BClassRepository extends JpaRepository<BClass, Long> {
}
