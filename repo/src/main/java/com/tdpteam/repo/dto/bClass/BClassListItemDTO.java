package com.tdpteam.repo.dto.bClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BClassListItemDTO {
    private Long id;
    private String name;
    private String teacherName;
    private int numberOfStudents;
    private String subjectName;
    private Date startDate;
    private Date estimatedEndDate;
    private int numberOfLessons;
}
