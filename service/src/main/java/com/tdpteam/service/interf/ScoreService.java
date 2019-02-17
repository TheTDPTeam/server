package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.score.ScoreForClassItemDTO;
import com.tdpteam.repo.entity.BClass;
import com.tdpteam.repo.entity.Score;
import com.tdpteam.repo.entity.Subject;
import com.tdpteam.repo.entity.user.Student;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Set;

public interface ScoreService {
    Set<Score> generateScoresForAllStudentsInThisClass(Set<Student> studentSet, Subject subject, BClass bClass);
    List<Score> getAllLatestScoreOfStudent(Long studentId);

    List<ScoreForClassItemDTO> getScoreListOfBClass(Long id);

    @Async
    void updateScore(Long id, Integer theory, Integer practical);
}
