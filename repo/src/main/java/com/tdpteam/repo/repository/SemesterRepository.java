package com.tdpteam.repo.repository;

import com.tdpteam.repo.entity.Course;
import com.tdpteam.repo.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
    int countByCourse(Course course);
    List<Semester> findAllByOrderByCourseDesc();
    List<Semester> findAllByCourse_IdOrderByCreatedAtAsc(Long id);
}
