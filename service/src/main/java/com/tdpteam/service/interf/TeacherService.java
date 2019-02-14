package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.SelectionItem;
import com.tdpteam.repo.dto.teacher.TeacherListItemDTO;
import com.tdpteam.repo.entity.user.Teacher;

import java.util.List;

public interface TeacherService{
    List<TeacherListItemDTO> getTeacherListDTO();
    Teacher findTeacherById(Long id);

    List<SelectionItem> getAllTeachersForSelection();
}
