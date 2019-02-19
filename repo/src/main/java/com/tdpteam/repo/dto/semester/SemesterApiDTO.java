package com.tdpteam.repo.dto.semester;

import com.tdpteam.repo.dto.subject.SubjectApiDTO;
import lombok.Data;

import java.util.List;

@Data
public class SemesterApiDTO {
    private String semesterName;
    private List<SubjectApiDTO> subjects;
}
