package com.tdpteam.repo.api.response;

import com.tdpteam.repo.dto.subject.SubjectScoreItemDTO;
import lombok.Data;

import java.util.List;

@Data
public class ScoreListResponse {
    private String semesterName;
    private List<SubjectScoreItemDTO> subjects;
}
