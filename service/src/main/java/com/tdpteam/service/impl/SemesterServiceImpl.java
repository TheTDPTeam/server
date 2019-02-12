package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.semester.SemesterListItemDTO;
import com.tdpteam.repo.dto.semester.SemesterSelectionItemDTO;
import com.tdpteam.repo.entity.Course;
import com.tdpteam.repo.entity.Semester;
import com.tdpteam.repo.repository.CourseRepository;
import com.tdpteam.repo.repository.SemesterRepository;
import com.tdpteam.service.exception.course.CourseNotFoundException;
import com.tdpteam.service.helper.Constants;
import com.tdpteam.service.interf.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SemesterServiceImpl implements SemesterService {
    private SemesterRepository semesterRepository;
    private CourseRepository courseRepository;

    @Autowired
    public SemesterServiceImpl(SemesterRepository semesterRepository, CourseRepository courseRepository) {
        this.semesterRepository = semesterRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<SemesterListItemDTO> getAllSemesters() {
        List<Semester> semesters = semesterRepository.findAllByOrderByCourseDesc();
        List<SemesterListItemDTO> semesterListItemDTOList = new ArrayList<>();
        semesters.forEach(semester -> semesterListItemDTOList.add(
                SemesterListItemDTO.builder()
                .id(semester.getId())
                .name(semester.getName())
                .courseCode(semester.getCourse().getCode())
                .isActivated(semester.isActivated())
                .numberOfSubjects(semester.getSubjects().size())
                .build()));
        return semesterListItemDTOList;
    }

    @Override
    public void saveSemester(Semester semester, Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(!optionalCourse.isPresent()){
            throw new CourseNotFoundException(courseId);
        }
        Course course = optionalCourse.get();
        semester.setCourse(course);
        semester.setName(Constants.SEMESTER_PREFIX + " " + (course.getSemesters().size() + 1));
        Set<Semester> semesterSet = course.getSemesters();
        semesterSet.add(semester);
        course.setSemesters(semesterSet);
        courseRepository.save(course);
        semesterRepository.save(semester);
    }

    @Override
    public void deleteSemester(Long id) {
        try{
            Optional<Semester> optionalSemester =  semesterRepository.findById(id);
            if(optionalSemester.isPresent()){
                Semester processingSemester = optionalSemester.get();
                Long courseId = processingSemester.getCourse().getId();
                semesterRepository.delete(processingSemester);
                List<Semester> remainingSemesters = semesterRepository.findAllByCourse_IdOrderByCreatedAtAsc(courseId);
                remainingSemesters.forEach(semester -> {
                    semester.setName(Constants.SEMESTER_PREFIX + " " + (remainingSemesters.indexOf(semester) + 1));
                    semesterRepository.save(semester);
                });
            }
        }catch (Exception ignored){}
    }

    @Override
    public List<SemesterSelectionItemDTO> getAllSemestersForSelection() {
        List<Semester> semesters = semesterRepository.findAllByOrderByCourseDesc();
        List<SemesterSelectionItemDTO> semesterSelectionItemDTOS = new ArrayList<>();
        semesters.forEach(semester -> semesterSelectionItemDTOS.add(
                new SemesterSelectionItemDTO(semester.getId(), semester.getName() + " of " + semester.getCourse().getCode())
        ));
        return semesterSelectionItemDTOS;
    }

    @Override
    public void changeActivation(Long id) {
        Optional<Semester> optionalSemester = semesterRepository.findById(id);
        if (optionalSemester.isPresent()) {
            Semester semester = optionalSemester.get();
            semester.setActivated(!semester.isActivated());
            semesterRepository.save(semester);
        }
    }
}
