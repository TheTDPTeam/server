package com.tdpteam.service.impl;

import com.tdpteam.repo.api.response.LearningProgressResponse;
import com.tdpteam.repo.api.response.ScoreListResponse;
import com.tdpteam.repo.dto.SelectionItem;
import com.tdpteam.repo.dto.score.ScoreStatus;
import com.tdpteam.repo.dto.student.StudentListItemDTO;
import com.tdpteam.repo.dto.subject.SubjectScoreItemDTO;
import com.tdpteam.repo.entity.*;
import com.tdpteam.repo.entity.user.Student;
import com.tdpteam.repo.helper.Constants;
import com.tdpteam.repo.repository.*;
import com.tdpteam.service.exception.student.StudentNotFoundException;
import com.tdpteam.service.interf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(!optionalStudent.isPresent()) return 0;
        List<BClass> bClasses = bClassRepository.getBClassesByStudentId(studentId);
        List<BClass> filteredBClasses = bClasses.stream().filter(bClass -> bClass.getSubject()
        .getSemester().getCourse().equals(optionalStudent.get().getBatch().getCourse())).collect(Collectors.toList());
        if(filteredBClasses.size() > 0){
            filteredBClasses.sort((o1, o2) -> o2.getSubject().getSemester().getName().compareTo(o1.getSubject().getSemester().getName()));
            return Integer.parseInt(filteredBClasses.get(0).getSubject().getSemester().getName().split(" ")[1]);
        }
        return 0;
    }

    @Override
    public LearningProgressResponse getStudentLearningProgressInfo(Long studentId) {
        Double averageScore = getCumulativeGradePointAverage(studentId);
        return LearningProgressResponse.builder()
                .cumulativeGradePointAverage(averageScore)
                .latestSemester(getLatestSemester(studentId))
                .gradeNeededToGetNextLevel(getGradeNeededToReachNextStateOfDegree(averageScore)).build();
    }

    private Double getGradeNeededToReachNextStateOfDegree(Double averageScore) {
        if(averageScore< Constants.PASSING_GRADE){
            return Constants.PASSING_GRADE - averageScore;
        }else if(averageScore<Constants.CREDIT_GRADE){
            return Constants.CREDIT_GRADE - averageScore;
        }else if(averageScore < Constants.DISTINCTION_GRADE){
            return Constants.DISTINCTION_GRADE - averageScore;
        }else {
            return 0.0d;
        }
    }

    private Double getCumulativeGradePointAverage(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(!optionalStudent.isPresent()) return null;
        Student student = optionalStudent.get();
        Course course = student.getBatch().getCourse();
        int numberOfTheoryScores = 0, numberOfPracticalScores = 0, numberOfProjects = 0;
        double theorySum = 0.0d, practicalSum = 0.0d, projectScoreSum = 0.0d;

        for(Semester semester : course.getSemesters()){
            for(Subject subject : semester.getSubjects()){
                Score score = scoreRepository.findTopByStudent_IdAndSubject_IdOrderByCreatedAtDesc(studentId,subject.getId());
                if(score != null){
                    if(subject.isProject()){
                        numberOfProjects++;
                        projectScoreSum += score.getPracticalScore();
                    }else {
                        if(subject.isHasTheoryExamination()){
                            numberOfTheoryScores++;
                            theorySum += score.getTheoryScore();
                        }

                        if(subject.isHasPracticalExamination()){
                            numberOfPracticalScores++;
                            practicalSum += score.getPracticalScore();
                        }
                    }
                }
            }
        }

        return getAverageScore(numberOfTheoryScores, numberOfPracticalScores, numberOfProjects, theorySum, practicalSum, projectScoreSum);
    }

    private double getAverageScore(int numberOfTheoryScores, int numberOfPracticalScores, int numberOfProjects, double theorySum, double practicalSum, double projectScoreSum) {
        double average = 0.0;
        if(numberOfTheoryScores > 0){
            average += (theorySum / numberOfTheoryScores) * 0.5;
        }
        if(numberOfPracticalScores > 0){
            average += (practicalSum / numberOfPracticalScores) * 0.25;
        }
        if(numberOfProjects > 0){
            average += (projectScoreSum/numberOfProjects) * 0.25;
        }
        return average / 20 * 100;
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
                .isProject(subject.getName().toLowerCase().contains("project"))
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
