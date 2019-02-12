package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.course.CourseDTO;
import com.tdpteam.repo.dto.course.CourseDetailDTO;
import com.tdpteam.repo.dto.course.CourseListItemDTO;
import com.tdpteam.repo.dto.course.CourseSelectionItemDTO;
import com.tdpteam.repo.entity.Course;
import com.tdpteam.repo.entity.Semester;
import com.tdpteam.repo.repository.CourseRepository;
import com.tdpteam.repo.repository.SemesterRepository;
import com.tdpteam.service.exception.course.CourseNotFoundException;
import com.tdpteam.service.helper.Constants;
import com.tdpteam.service.interf.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;
    private SemesterRepository semesterRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, SemesterRepository semesterRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CourseListItemDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAllByOrderByCreatedAtDesc();
        List<CourseListItemDTO> courseListItemDTOS = new ArrayList<>();
        courses.forEach(course -> {
                    courseListItemDTOS.add(
                            CourseListItemDTO.builder()
                                    .id(course.getId())
                                    .name(course.getName())
                                    .code(course.getCode())
                                    .numberOfSemesters(course.getSemesters().size())
                                    .numberOfBatches(course.getBatches().size())
                                    .numberOfSubjects(course.getSemesters().stream()
                                            .mapToInt(semester -> semester.getSubjects().size())
                                            .sum())
                                    .isActivated(course.isActivated())
                                    .build());
                }
        );
        return courseListItemDTOS;
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public Course findById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent()) {
            throw new CourseNotFoundException(id);
        }
        return optionalCourse.get();
    }

    @Override
    public void updateCourse(Long id, CourseDTO courseDTO) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            modelMapper.map(courseDTO, course);
            saveCourse(course);
        }
    }

    @Override
    public void deleteCourse(Long id) {
        try {
            courseRepository.deleteById(id);
        } catch (Exception ignored) {
        }
    }

    @Override
    public CourseDetailDTO getCourseDetails(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent()) {
            throw new CourseNotFoundException(id);
        }
        Course course = optionalCourse.get();
        return CourseDetailDTO.builder()
                .name(course.getName())
                .code(course.getCode())
                .isActivated(course.isActivated())
                .batchSet(course.getBatches())
                .semesterSet(course.getSemesters())
                .build();
    }

    @Override
    public List<CourseSelectionItemDTO> getAllCoursesForSelection() {
        List<Course> courses = courseRepository.findAllByOrderByCreatedAtDesc();
        List<CourseSelectionItemDTO> courseListItemDTOS = new ArrayList<>();
        courses.forEach(course -> courseListItemDTOS.add(
                new CourseSelectionItemDTO(course.getId(), course.getCode())
        ));
        return courseListItemDTOS;
    }

    @Override
    public void changeActivation(Long id) {
        Optional<Course> optionalAccount = courseRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Course course = optionalAccount.get();
            course.setActivated(!course.isActivated());
            courseRepository.save(course);
        }
    }
}
