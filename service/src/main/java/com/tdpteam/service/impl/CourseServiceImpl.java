package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.course.CourseListItemDTO;
import com.tdpteam.repo.entity.Course;
import com.tdpteam.repo.repository.CourseRepository;
import com.tdpteam.repo.repository.SemesterRepository;
import com.tdpteam.service.exception.CourseNotFoundException;
import com.tdpteam.service.interf.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;
    private SemesterRepository semesterRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, SemesterRepository semesterRepository) {
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
    }

    @Override
    public List<CourseListItemDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAllByOrOrderByCreatedAtDesc();
        List<CourseListItemDTO> courseListItemDTOS = new ArrayList<>();
        courses.forEach(course -> courseListItemDTOS.add(
                CourseListItemDTO.builder()
                        .id(course.getId())
                        .name(course.getName())
                        .code(course.getCode())
                        .numberOfSemester(course.getSemesters().size())
                        .numberOfBatches(course.getBatches().size())
                        .numberOfSubjects(semesterRepository.countByCourse(course))
                        .build()));
        return courseListItemDTOS;
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Course findById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if(!optionalCourse.isPresent()){
            throw new CourseNotFoundException();
        }
        return optionalCourse.get();
    }
}
