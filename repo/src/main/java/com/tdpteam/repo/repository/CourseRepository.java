package com.tdpteam.repo.repository;

import com.tdpteam.repo.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByOrderByCreatedAtDesc();
    List<Course> findAllByOrderByCreatedAtAsc();
}
