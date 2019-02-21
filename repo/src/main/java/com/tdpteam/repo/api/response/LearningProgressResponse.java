package com.tdpteam.repo.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningProgressResponse {
    private Double cumulativeGradePointAverage;
    private int latestSemester;
    private Double gradeNeededToGetNextLevel;
}
