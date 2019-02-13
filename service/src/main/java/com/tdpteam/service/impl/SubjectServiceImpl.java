package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.subject.SubjectDTO;
import com.tdpteam.repo.dto.subject.SubjectListItemDTO;
import com.tdpteam.repo.entity.Semester;
import com.tdpteam.repo.entity.Subject;
import com.tdpteam.repo.repository.SemesterRepository;
import com.tdpteam.repo.repository.SubjectRepository;
import com.tdpteam.service.interf.SubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {
    private SubjectRepository subjectRepository;
    private SemesterRepository semesterRepository;
    private ModelMapper modelMapper;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository, SemesterRepository semesterRepository, ModelMapper modelMapper) {
        this.subjectRepository = subjectRepository;
        this.semesterRepository = semesterRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void deleteSubject(Long id) {

    }

    @Override
    public void saveSubject(SubjectDTO subjectDTO) {
        Subject subject = modelMapper.map(subjectDTO, Subject.class);
        Optional<Semester> optionalSemester = semesterRepository.findById(subjectDTO.getSemesterId());
        if(optionalSemester.isPresent()){
           subject.setSemester(optionalSemester.get());
           subjectRepository.save(subject);
        }
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
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();
            subject.setActivated(!subject.isActivated());
            subjectRepository.save(subject);
        }
    }
}
