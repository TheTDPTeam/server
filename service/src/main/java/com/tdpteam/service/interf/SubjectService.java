package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.SelectionItem;
import com.tdpteam.repo.dto.subject.SubjectDTO;
import com.tdpteam.repo.dto.subject.SubjectListItemDTO;
import com.tdpteam.repo.entity.Subject;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface SubjectService extends ActivationService{
    void deleteSubject(Long id);

    @Async
    void saveSubject(SubjectDTO subjectDTO);

    List<SubjectListItemDTO> getAllSubjects();

    List<SelectionItem> getAllSubjectsForSelection();

    Subject getSubjectById(Long id);
}
