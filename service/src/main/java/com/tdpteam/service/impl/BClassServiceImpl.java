package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.bClass.BClassDTO;
import com.tdpteam.repo.dto.bClass.BClassDetailDTO;
import com.tdpteam.repo.dto.bClass.BClassListItemDTO;
import com.tdpteam.repo.entity.Attendance;
import com.tdpteam.repo.entity.BClass;
import com.tdpteam.repo.entity.Score;
import com.tdpteam.repo.entity.Subject;
import com.tdpteam.repo.entity.user.Student;
import com.tdpteam.repo.entity.user.Teacher;
import com.tdpteam.repo.repository.BClassRepository;
import com.tdpteam.repo.repository.StudentRepository;
import com.tdpteam.repo.repository.SubjectRepository;
import com.tdpteam.repo.repository.TeacherRepository;
import com.tdpteam.service.exception.bClass.BClassNotFoundException;
import com.tdpteam.service.helper.RoleType;
import com.tdpteam.service.helper.SetHelpers;
import com.tdpteam.service.interf.AccountService;
import com.tdpteam.service.interf.AttendanceService;
import com.tdpteam.service.interf.BClassService;
import com.tdpteam.service.interf.ScoreService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BClassServiceImpl implements BClassService {
    private BClassRepository bClassRepository;
    private TeacherRepository teacherRepository;
    private SubjectRepository subjectRepository;
    private StudentRepository studentRepository;
    private AccountService accountService;
    private AttendanceService attendanceService;
    private ScoreService scoreService;

    private ModelMapper modelMapper;

    @Autowired
    public BClassServiceImpl(BClassRepository bClassRepository,
                             TeacherRepository teacherRepository,
                             SubjectRepository subjectRepository,
                             StudentRepository studentRepository,
                             AccountService accountService, AttendanceService attendanceService,
                             ScoreService scoreService, ModelMapper modelMapper) {
        this.bClassRepository = bClassRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
        this.accountService = accountService;
        this.attendanceService = attendanceService;
        this.scoreService = scoreService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BClassListItemDTO> getAllBClassesForRendering() {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString();

        List<BClass> bClasses;
        if(role.equals(RoleType.TEACHER.toString())){
            Long teacherId = accountService.getCurrentUserId();
            bClasses = bClassRepository.findAllByTeacher_IdOrderByCreatedAtDesc(teacherId);
        }else{
            bClasses = bClassRepository.findAllByOrderByCreatedAtDesc();
        }
        List<BClassListItemDTO> bClassListItemDTOList = new ArrayList<>();
        bClasses.forEach(bClass -> bClassListItemDTOList.add(
                BClassListItemDTO.builder()
                        .id(bClass.getId())
                        .name(bClass.getName())
                        .numberOfLessons(bClass.getSubject().getNumberOfLessons())
                        .numberOfStudents(bClass.getStudents().size())
                        .subjectName(bClass.getSubject().getName())
                        .teacherName(bClass.getTeacher().getAccount().getUserDetail().getFullName())
                        .startDate(bClass.getStartDate())
                        .estimatedEndDate(bClass.getEstimatedEndDate())
                        .isActivated(bClass.isActivated()).build()
        ));
        return bClassListItemDTOList;
    }

    @Override
    public BClass getBClassById(Long id) {
        return null;
    }

    @Override
    public void createBClass(BClassDTO bClassDTO) {
        BClass bClass = modelMapper.map(bClassDTO, BClass.class);
        Optional<Teacher> optionalTeacher = teacherRepository.findById(bClassDTO.getTeacherId());
        Optional<Subject> optionalSubject = subjectRepository.findById(bClassDTO.getSubjectId());
        if(optionalSubject.isPresent() && optionalTeacher.isPresent()){
            String calendar = String.join(",",bClassDTO.getCalendarSelectedValues());
            Subject subject = optionalSubject.get();
            Set<Student> studentSet = getStudentsForBClass(bClassDTO);
            Set<Attendance> attendances = attendanceService.generateAttendanceSetForClass(
                    calendar,
                    bClassDTO.getStartDate(),
                    subject.getNumberOfLessons());
            bClass.setTeacher(optionalTeacher.get());
            bClass.setSubject(subject);
            bClass.setCalendar(calendar);
            bClass.setStudents(studentSet);
            bClass.setEstimatedEndDate(SetHelpers.getLastElement(attendances).getCheckingDate());
            Set<Attendance> attendancesWithStudents = attendanceService.updateAttendancesWithStudentsAndBClass(studentSet, attendances,bClass);
            bClass.setAttendances(attendancesWithStudents);
            Set<Score> scores = scoreService.generateScoresForAllStudentsInThisClass(studentSet, subject, bClass);
            bClass.setScores(scores);
            bClassRepository.save(bClass);
        }
    }

    private Set<Student> getStudentsForBClass(BClassDTO bClassDTO) {
        Set<Student> studentSet = new HashSet<>();
        bClassDTO.getSelectedStudents().forEach(studentId -> {
            Optional<Student> optionalStudent = studentRepository.findById(studentId);
            optionalStudent.ifPresent(studentSet::add);
        });
        return studentSet;
    }

    @Override
    public void deleteCourse(Long id) {
        try {
            bClassRepository.deleteById(id);
        } catch (Exception ignored) {
        }
    }

    @Override
    public BClassDetailDTO getBClassDetail(Long id) {
        Optional<BClass> optionalBClass = bClassRepository.findById(id);
        if(!optionalBClass.isPresent()){
            throw new BClassNotFoundException(id);
        }
        BClass bClass = optionalBClass.get();
        BClassDetailDTO bClassDetailDTO = modelMapper.map(bClass, BClassDetailDTO.class);
        bClassDetailDTO.setNumberOfLessons(bClass.getSubject().getNumberOfLessons());
        bClassDetailDTO.setNumberOfStudents(bClass.getStudents().size());
        bClassDetailDTO.setSubjectName(bClass.getSubject().getName());
        bClassDetailDTO.setTeacherName(bClass.getTeacher().getAccount().getUserDetail().getFullName());
        return bClassDetailDTO;
    }

    @Override
    public void changeActivation(Long id) {
        Optional<BClass> optionalBClass = bClassRepository.findById(id);
        if (optionalBClass.isPresent()) {
            BClass bClass = optionalBClass.get();
            bClass.setActivated(!bClass.isActivated());
            bClassRepository.save(bClass);
        }
    }
}
