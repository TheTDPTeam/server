package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.SelectionItem;
import com.tdpteam.repo.dto.semester.SemesterListItemDTO;
import com.tdpteam.repo.entity.Semester;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface SemesterService extends ActivationService{
    List<SemesterListItemDTO> getAllSemesters();

    @Async
    void saveSemester(Semester semester, Long courseId);

    @Async
    void deleteSemester(Long id);

    List<SelectionItem> getAllSemestersForSelection();
}
