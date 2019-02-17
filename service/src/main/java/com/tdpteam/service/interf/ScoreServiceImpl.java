package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.score.ScoreForClassItemDTO;
import com.tdpteam.repo.entity.BClass;
import com.tdpteam.repo.entity.Score;
import com.tdpteam.repo.entity.Subject;
import com.tdpteam.repo.entity.user.Student;
import com.tdpteam.repo.repository.ScoreRepository;
import com.tdpteam.service.exception.score.ScoreNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ScoreServiceImpl implements ScoreService {
    private ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public Set<Score> generateScoresForAllStudentsInThisClass(Set<Student> studentSet, Subject subject, BClass bClass) {
        Set<Score> scores = new HashSet<>();
        studentSet.forEach(student -> {
            Score score = new Score();
            score.setBClass(bClass);
            score.setStudent(student);
            score.setSubject(subject);
            if(subject.isHasPracticalExamination()) score.setPracticalScore(0);

            if(subject.isHasTheoryExamination()) score.setTheoryScore(0);
            scores.add(score);
        });
        return scores;
    }

    @Override
    public List<Score> getAllLatestScoreOfStudent(Long studentId) {
        return null;
    }

    @Override
    public List<ScoreForClassItemDTO> getScoreListOfBClass(Long id) {
        List<Score> scores = scoreRepository.findAllByBClass_Id(id);
        List<ScoreForClassItemDTO> scoreForClassItemDTOList = new ArrayList<>();
        scores.forEach(score -> scoreForClassItemDTOList.add(
                ScoreForClassItemDTO.builder()
                        .id(score.getId())
                        .studentName(score.getStudent().getAccount().getUserDetail().getFullName())
                        .practicalScore(score.getPracticalScore())
                        .isPracticalScorePassed(score.isPracticalScorePassed())
                        .isTheoryScorePassed(score.isTheoryScorePassed())
                        .theoryScore(score.getTheoryScore())
                        .build()
        ));
        scoreForClassItemDTOList.sort(Comparator.comparing(ScoreForClassItemDTO::getStudentName));
        return scoreForClassItemDTOList;
    }

    @Override
    public void updateScore(Long id, Integer theory, Integer practical) {
        Optional<Score> optionalScore = scoreRepository.findById(id);
        if(!optionalScore.isPresent()){
            throw new ScoreNotFoundException(id);
        }
        Score score = optionalScore.get();
        score.setPracticalScore(practical);
        score.setTheoryScore(theory);
        scoreRepository.save(score);
    }
}
