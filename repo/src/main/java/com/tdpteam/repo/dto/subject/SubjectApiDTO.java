package com.tdpteam.repo.dto.subject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectApiDTO {
    private String name;
    private String description;
    private int numberOfLessons;
    private boolean hasTheoryExamination;
    private boolean hasPracticalExamination;
}
