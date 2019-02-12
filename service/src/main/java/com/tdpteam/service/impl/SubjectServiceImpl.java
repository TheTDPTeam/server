package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.subject.SubjectListItemDTO;
import com.tdpteam.repo.entity.Subject;
import com.tdpteam.repo.repository.SubjectRepository;
import com.tdpteam.service.interf.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {
    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void deleteSubject(Long id) {

    }

    @Override
    public void saveSubject(Subject subject) {

    }

    @Override
    public List<SubjectListItemDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAllByOrderBySemesterDesc();
        List<SubjectListItemDTO> subjectListItemDTOList = new ArrayList<>();
        subjects.forEach(subject -> subjectListItemDTOList.add(
                SubjectListItemDTO.builder()
                        .id(subject.getId())
                        .name(subject.getName())
                        .courseCode(subject.getSemester().getCourse().getCode())
                        .semesterName(subject.getSemester().getName())
                        .subjectOrder(subject.getSubjectOrder())
                        .isActivated(subject.isActivated())
                        .build()
        ));
        return subjectListItemDTOList;
    }

    @Override
    public void changeActivation(Long id) {

    }
}
