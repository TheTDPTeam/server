package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.course.CourseListItemDTO;
import com.tdpteam.repo.entity.Course;

import java.util.List;

public interface CourseService {
    List<CourseListItemDTO> getAllCourses();

    void saveCourse(Course course);

    Course findById(Long id);
}
