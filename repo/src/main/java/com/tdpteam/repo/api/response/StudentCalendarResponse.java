package com.tdpteam.repo.api.response;

import com.tdpteam.repo.dto.attendance.AttendanceApiItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentCalendarResponse {
    private String subjectName;
    private String teacherName;
    private String semesterName;
    private String calendar;
    private List<AttendanceApiItemDTO> attendances;
}
