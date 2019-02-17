package com.tdpteam.repo.repository;

import com.tdpteam.repo.entity.BClass;
import com.tdpteam.repo.entity.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAccount_IsActivated(boolean isActivated);
    @Query("select distinct s from Student s join s.bClasses b where b.id = :id")
    List<Student> findAllByBClassId(Long id);
}
