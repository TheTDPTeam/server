package com.tdpteam.repo.dto.semester;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemesterListItemDTO {
    private Long id;
    private String name;
    private String courseCode;
    private int numberOfSubjects;
    private boolean isActivated;
}
