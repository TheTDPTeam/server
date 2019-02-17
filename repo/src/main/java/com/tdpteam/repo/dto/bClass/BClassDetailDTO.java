package com.tdpteam.repo.dto.bClass;

import com.tdpteam.repo.entity.Attendance;
import com.tdpteam.repo.entity.user.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BClassDetailDTO {
    private String id;
    private String name;
    private String teacherName;
    private int numberOfStudents;
    private String subjectName;
    private Date startDate;
    private Date estimatedEndDate;
    private int numberOfLessons;
    private boolean isActivated;
    private Set<Attendance> attendances;
    private String calendar;
    private Set<Student> students;
}
