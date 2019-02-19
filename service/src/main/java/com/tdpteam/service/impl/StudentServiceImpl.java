package com.tdpteam.service.impl;

import com.tdpteam.repo.api.response.ScoreListResponse;
import com.tdpteam.repo.dto.SelectionItem;
import com.tdpteam.repo.dto.score.ScoreStatus;
import com.tdpteam.repo.dto.student.StudentListItemDTO;
import com.tdpteam.repo.dto.subject.SubjectScoreItemDTO;
import com.tdpteam.repo.entity.*;
import com.tdpteam.repo.entity.user.Student;
import com.tdpteam.repo.repository.*;
import com.tdpteam.service.exception.student.StudentNotFoundException;
import com.tdpteam.service.interf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private BatchRepository batchRepository;
    private AttendanceRepository attendanceRepository;
    private ScoreRepository scoreRepository;
    private BClassRepository bClassRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,
                              BatchRepository batchRepository,
                              AttendanceRepository attendanceRepository,
                              ScoreRepository scoreRepository,
                              BClassRepository bClassRepository) {
        this.studentRepository = studentRepository;
        this.batchRepository = batchRepository;
        this.attendanceRepository = attendanceRepository;
        this.scoreRepository = scoreRepository;
        this.bClassRepository = bClassRepository;
    }

    @Override
    public Student findStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (!optionalStudent.isPresent()) {
            throw new StudentNotFoundException(id);
        }
        return optionalStudent.get();
    }

    @Override
    public List<StudentListItemDTO> getStudentListDTO() {
        List<Student> students = studentRepository.findAll();
        List<StudentListItemDTO> studentListItemDTOList = new ArrayList<>();
        students.forEach(student -> studentListItemDTOList.add(
                StudentListItemDTO.builder()
                        .id(student.getId())
                        .fullName(student.getAccount().getUserDetail().getFullName())
                        .batchName(student.getBatch() == null ? "No batch" : student.getBatch().getName())
                        .email(student.getAccount().getEmail())
                        .numberOfClasses(student.getBClasses().size())
                        .isActivated(student.getAccount().isActivated())
                        .build()
        ));
        return studentListItemDTOList;
    }

    @Override
    public void changeBatch(Long id, Long batchId) {
        Optional<Batch> optionalBatch = batchRepository.findById(batchId);
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalBatch.isPresent() && optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            student.setBatch(optionalBatch.get());
            studentRepository.save(student);
        }
    }

    @Override
    public List<SelectionItem> getAvailableStudentsForJoiningClass() {
        List<Student> students = studentRepository.findAllByAccount_IsActivated(true);
        List<SelectionItem> selectionItems = new ArrayList<>();
        students.forEach(student -> {
            if (student.getBatch() != null) {
                selectionItems.add(
                        new SelectionItem(student.getId(),
                                student.getAccount().getUserDetail().getFullName() + "|" + student.getBatch().getName())
                );
            }
        });
        return selectionItems;
    }

    @Override
    public List<ScoreListResponse> getStudentScoreListById(Long studentId) {
        Student student = findStudentById(studentId);
        Batch batch = student.getBatch();
        if (batch == null) {
            return null;
        }
        List<ScoreListResponse> scoreListResponses = new ArrayList<>();
        Course course = batch.getCourse();
        List<Semester> semesters = new ArrayList<>(course.getSemesters());
        semesters.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        semesters.forEach(semester -> {
            List<SubjectScoreItemDTO> subjectScoreItemDTOList = new ArrayList<>();
            List<Subject> subjects = new ArrayList<>(semester.getSubjects());
            subjects.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
            subjects.forEach(subject -> addSubjectScoreItemToList(studentId, subjectScoreItemDTOList, subject));
            if(subjectScoreItemDTOList.size() != 0){
                ScoreListResponse scoreListResponse = new ScoreListResponse();
                scoreListResponse.setSemesterName(semester.getName());
                scoreListResponse.setSubjects(subjectScoreItemDTOList);
                scoreListResponses.add(scoreListResponse);
            }
        });
        return scoreListResponses;
    }

    @Override
    public int getLatestSemester(Long studentId) {
        List<BClass> bClasses = bClassRepository.getBClassesByStudentId(studentId);
        if(bClasses.size() > 0){
            bClasses.sort((o1, o2) -> o2.getSubject().getSemester().getName().compareTo(o1.getSubject().getSemester().getName()));
            return Integer.parseInt(bClasses.get(0).getSubject().getSemester().getName().split(" ")[1]);
        }
        return 0;
    }

    private void addSubjectScoreItemToList(Long studentId, List<SubjectScoreItemDTO> subjectScoreItemDTOList, Subject subject) {
        Score score = scoreRepository.findTopByStudent_IdAndSubject_IdOrderByCreatedAtDesc(studentId, subject.getId());
        if (score != null) {
            Integer theoryScore = score.getTheoryScore();
            Integer practicalScore = score.getPracticalScore();
            ScoreStatus theoryScoreStatus = getScoreStatus(
                    theoryScore,
                    subject.isHasTheoryExamination(),
                    score.isTheoryScorePassed());
            ScoreStatus practicalScoreStatus = getScoreStatus(
                    practicalScore,
                    subject.isHasPracticalExamination(),
                    score.isPracticalScorePassed());
            subjectScoreItemDTOList.add(
                    buildSubjectScoreItem(studentId, subject, score, theoryScore, practicalScore, theoryScoreStatus, practicalScoreStatus)
            );
        }
    }

    private SubjectScoreItemDTO buildSubjectScoreItem(Long studentId, Subject subject, Score score, Integer theoryScore, Integer practicalScore, ScoreStatus theoryScoreStatus, ScoreStatus practicalScoreStatus) {
        return SubjectScoreItemDTO.builder()
                .subjectName(subject.getName())
                .theoryScore(Double.valueOf(theoryScore))
                .practicalScore(Double.valueOf(practicalScore))
                .theoryScoreStatus(
                        theoryScoreStatus)
                .practicalScoreStatus(
                        practicalScoreStatus)
                .attendingRate(
                        getAttendanceRate(
                                studentId,
                                score.getBClass().getId(),
                                subject.getNumberOfLessons()))
                .isSuccess(getIsSuccess(theoryScoreStatus, practicalScoreStatus)).build();
    }

    private boolean getIsSuccess(ScoreStatus theoryScoreStatus, ScoreStatus practicalScoreStatus) {
        return theoryScoreStatus == ScoreStatus.Passed && practicalScoreStatus == ScoreStatus.Passed;
    }

    private String getAttendanceRate(Long studentId, Long classId, int numberOfLessons) {
        List<Attendance> attendances = attendanceRepository.findAllByStudent_IdAndBClass_IdAndStatus(studentId, classId, AttendanceStatus.Attended);
        return attendances.size() + "/" + numberOfLessons;
    }

    private ScoreStatus getScoreStatus(Integer score, boolean hasExamination, boolean isScorePassed) {
        if (!hasExamination) {
            return ScoreStatus.NotCounted;
        }
        if (score == null) {
            return ScoreStatus.NotYet;
        }
        return isScorePassed ? ScoreStatus.Passed : ScoreStatus.Failed;
    }
}
