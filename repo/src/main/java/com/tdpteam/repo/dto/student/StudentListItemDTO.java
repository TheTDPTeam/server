package com.tdpteam.repo.dto.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentListItemDTO {
    private Long id;
    private String fullName;
    private String email;
    private String batchName;
    private int numberOfClasses;
}
