package com.tdpteam.repo.dto.batch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchListItemDTO {
    private Long id;
    private String name;
    private String code;
    private String courseCode;
    private Date startDate;
    private Date endDate;
    private int numberOfStudents;
    private boolean isActivated;
}
