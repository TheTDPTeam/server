package com.tdpteam.repo.dto.score;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreForClassItemDTO {
    private Long id;
    private String studentName;
    private Integer theoryScore;
    private Integer practicalScore;
    private boolean isPracticalScorePassed;
    private boolean isTheoryScorePassed;
}
