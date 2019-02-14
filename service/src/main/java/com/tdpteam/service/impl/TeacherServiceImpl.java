package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.SelectionItem;
import com.tdpteam.repo.dto.teacher.TeacherListItemDTO;
import com.tdpteam.repo.entity.user.Teacher;
import com.tdpteam.repo.repository.TeacherRepository;
import com.tdpteam.service.exception.teacher.TeacherNotFoundException;
import com.tdpteam.service.interf.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public List<TeacherListItemDTO> getTeacherListDTO() {
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherListItemDTO> teacherListItemDTOList = new ArrayList<>();
        teachers.forEach(teacher -> teacherListItemDTOList.add(
                TeacherListItemDTO.builder()
                        .id(teacher.getId())
                        .fullName(teacher.getAccount().getUserDetail().getFullName())
                        .email(teacher.getAccount().getEmail())
                        .numberOfClasses(teacher.getBClasses().size())
                        .isActivated(teacher.getAccount().isActivated())
                        .build()
        ));
        return teacherListItemDTOList;
    }

    @Override
    public Teacher findTeacherById(Long id) {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if(!optionalTeacher.isPresent()){
            throw new TeacherNotFoundException(id);
        }
        return optionalTeacher.get();
    }

    @Override
    public List<SelectionItem> getAllTeachersForSelection() {
        List<Teacher> teachers = teacherRepository.findAll();
        List<SelectionItem> courseListItemDTOS = new ArrayList<>();
        teachers.forEach(teacher -> courseListItemDTOS.add(
                new SelectionItem(teacher.getId(), teacher.getAccount().getUserDetail().getFullName())
        ));
        return courseListItemDTOS;
    }
}
