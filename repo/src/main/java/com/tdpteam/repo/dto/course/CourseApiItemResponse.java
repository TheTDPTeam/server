package com.tdpteam.repo.dto.course;

import com.tdpteam.repo.dto.semester.SemesterApiDTO;
import lombok.Data;

import java.util.List;

@Data
public class CourseApiItemResponse {
    private String courseCode;
    private List<SemesterApiDTO> semesters;
}
