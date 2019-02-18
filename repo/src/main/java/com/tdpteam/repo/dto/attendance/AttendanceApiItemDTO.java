package com.tdpteam.repo.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceApiItemDTO {
    private Date checkingDate;
    private String status;
}
