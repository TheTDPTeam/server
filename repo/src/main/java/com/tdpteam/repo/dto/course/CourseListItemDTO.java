package com.tdpteam.repo.dto.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseListItemDTO {
    private Long id;
    private String name;
    private String code;
    private int numberOfSemester;
    private int numberOfSubjects;
    private int numberOfBatches;
}
