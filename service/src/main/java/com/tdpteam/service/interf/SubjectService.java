package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.subject.SubjectDTO;
import com.tdpteam.repo.dto.subject.SubjectListItemDTO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface SubjectService extends ActivationService{
    void deleteSubject(Long id);

    @Async
    void saveSubject(SubjectDTO subjectDTO);

    List<SubjectListItemDTO> getAllSubjects();
}
