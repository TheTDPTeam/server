package com.tdpteam.repo.repository;

import com.tdpteam.repo.entity.BClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BClassRepository extends JpaRepository<BClass, Long> {
    List<BClass> findAllByOrderByCreatedAtDesc();
    List<BClass> findAllByTeacher_IdOrderByCreatedAtDesc(Long id);
    @Query("select distinct b from BClass b join b.students s where s.id = :id order by b.createdAt desc")
    List<BClass> getBClassesByStudentId(Long id);
}
