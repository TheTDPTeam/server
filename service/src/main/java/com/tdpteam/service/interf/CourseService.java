package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.SelectionItem;
import com.tdpteam.repo.api.response.CourseApiItemResponse;
import com.tdpteam.repo.dto.course.CourseDTO;
import com.tdpteam.repo.dto.course.CourseDetailDTO;
import com.tdpteam.repo.dto.course.CourseListItemDTO;
import com.tdpteam.repo.entity.Course;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface CourseService extends ActivationService{
    List<CourseListItemDTO> getAllCourses();

    void saveCourseFromCourseDTO(CourseDTO courseDTO);

    Course findById(Long id);

    @Async
    void updateCourse(Long id, CourseDTO courseDTO);

    @Async
    void deleteCourse(Long id);

    CourseDetailDTO getCourseDetails(Long id);

    List<SelectionItem> getAllCoursesForSelection();

    CourseDTO getCourseDTO(Long id);

    String redirectToCourseList();

    List<CourseApiItemResponse> getAllCourseInfo();
}
