package com.tdpteam.repo.dto.attendance;

import com.tdpteam.repo.entity.Attendance;
import lombok.Data;

import java.util.List;

@Data
public class AttendanceOfClassDTO {
    private String studentName;
    private List<Attendance> attendanceList;
}
