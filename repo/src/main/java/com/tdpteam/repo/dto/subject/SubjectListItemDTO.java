package com.tdpteam.repo.dto.subject;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectListItemDTO {
    private Long id;
    private String name;
    private int subjectOrder;
    private String courseCode;
    private String semesterName;
    private boolean isProject;
    private boolean isActivated;
}
