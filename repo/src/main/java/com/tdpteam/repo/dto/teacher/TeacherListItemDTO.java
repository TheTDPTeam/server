package com.tdpteam.repo.dto.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherListItemDTO {
    private Long id;
    private String fullName;
    private String email;
    private int numberOfClasses;
    private boolean isActivated;
}
