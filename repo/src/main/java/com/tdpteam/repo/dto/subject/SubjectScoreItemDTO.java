package com.tdpteam.repo.dto.subject;

import com.tdpteam.repo.dto.score.ScoreStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectScoreItemDTO {
    private String subjectName;
    private Double theoryScore;
    private ScoreStatus theoryScoreStatus;
    private Double practicalScore;
    private ScoreStatus practicalScoreStatus;
    private String attendingRate;
    private boolean isProject;
    private boolean isSuccess;
}