package com.tdpteam.repo.repository;

import com.tdpteam.repo.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findAllByBClass_Id(Long id);
    Score findTopByStudent_IdAndSubject_IdOrderByCreatedAtDesc(Long studentId, Long subjectId);
}
