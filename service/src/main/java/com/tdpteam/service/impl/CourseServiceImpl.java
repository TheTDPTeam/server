package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.SelectionItem;
import com.tdpteam.repo.api.response.CourseApiItemResponse;
import com.tdpteam.repo.dto.course.CourseDTO;
import com.tdpteam.repo.dto.course.CourseDetailDTO;
import com.tdpteam.repo.dto.course.CourseListItemDTO;
import com.tdpteam.repo.dto.semester.SemesterApiDTO;
import com.tdpteam.repo.dto.subject.SubjectApiDTO;
import com.tdpteam.repo.entity.Batch;
import com.tdpteam.repo.entity.Course;
import com.tdpteam.repo.entity.Semester;
import com.tdpteam.repo.entity.Subject;
import com.tdpteam.repo.entity.base.BaseEntityAudit;
import com.tdpteam.repo.repository.CourseRepository;
import com.tdpteam.service.exception.course.CourseNotFoundException;
import com.tdpteam.service.interf.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
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
    public void saveCourseFromCourseDTO(CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
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
            courseRepository.save(course);
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
        List<Semester> semesters = new ArrayList<>(course.getSemesters());
        List<Batch> batches = new ArrayList<>(course.getBatches());
        semesters.sort(Comparator.comparing(Semester::getName));
        batches.sort(Comparator.comparing(Batch::getCode));
        return CourseDetailDTO.builder()
                .name(course.getName())
                .code(course.getCode())
                .isActivated(course.isActivated())
                .batches(batches)
                .semesters(semesters)
                .build();
    }

    @Override
    public List<SelectionItem> getAllCoursesForSelection() {
        List<Course> courses = courseRepository.findAllByOrderByCreatedAtDesc();
        List<SelectionItem> courseListItemDTOS = new ArrayList<>();
        courses.forEach(course -> courseListItemDTOS.add(
                new SelectionItem(course.getId(), course.getCode())
        ));
        return courseListItemDTOS;
    }

    @Override
    public CourseDTO getCourseDTO(Long id) {
        Course course = findById(id);
        CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);
        courseDTO.setIsActivated(course.isActivated());
        return courseDTO;
    }

    @Override
    public String redirectToCourseList() {
        return "redirect:/cms/courses";
    }

    @Override
    public List<CourseApiItemResponse> getAllCourseInfo() {
        List<Course> courses = courseRepository.findAllByOrderByCreatedAtDesc();
        List<CourseApiItemResponse> courseApiItemResponses = new ArrayList<>();
        courses.forEach(course -> {
            List<SemesterApiDTO> semesterApiDTOList = new ArrayList<>();
            List<Semester> semesters = new ArrayList<>(course.getSemesters());
            semesters.sort(Comparator.comparing(BaseEntityAudit::getCreatedAt));
            semesters.forEach(semester -> {
                List<SubjectApiDTO> subjectApiDTOList = new ArrayList<>();
                List<Subject> subjects = new ArrayList<>(semester.getSubjects());
                subjects.sort(Comparator.comparing(BaseEntityAudit::getCreatedAt));
                subjects.forEach(subject -> {
                    subjectApiDTOList.add(
                            modelMapper.map(subject, SubjectApiDTO.class)
                    );
                });
                if(subjectApiDTOList.size()>0){
                    SemesterApiDTO semesterApiDTO = new SemesterApiDTO();
                    semesterApiDTO.setSemesterName(semester.getName());
                    semesterApiDTO.setSubjects(subjectApiDTOList);
                    semesterApiDTOList.add(semesterApiDTO);
                }
            });
            if(semesterApiDTOList.size()>0){
                CourseApiItemResponse courseApiItemResponse = new CourseApiItemResponse();
                courseApiItemResponse.setCourseCode(course.getCode());
                courseApiItemResponse.setSemesters(semesterApiDTOList);
                courseApiItemResponses.add(courseApiItemResponse);
            }
        });
        return courseApiItemResponses;
    }

    @Override
    public void changeActivation(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.setActivated(!course.isActivated());
            courseRepository.save(course);
        }
    }
}
