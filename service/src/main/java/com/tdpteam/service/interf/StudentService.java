package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.SelectionItem;
import com.tdpteam.repo.dto.student.StudentListItemDTO;
import com.tdpteam.repo.entity.user.Student;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface StudentService {
    Student findStudentById(Long id);

    List<StudentListItemDTO> getStudentListDTO();

    @Async
    void changeBatch(Long id, Long batchId);

    List<SelectionItem> getAvailableStudentsForJoiningClass();
}
