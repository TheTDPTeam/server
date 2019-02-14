package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.student.StudentListItemDTO;
import com.tdpteam.repo.entity.Batch;
import com.tdpteam.repo.entity.user.Student;
import com.tdpteam.repo.repository.BatchRepository;
import com.tdpteam.repo.repository.StudentRepository;
import com.tdpteam.service.exception.student.StudentNotFoundException;
import com.tdpteam.service.interf.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private BatchRepository batchRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, BatchRepository batchRepository) {
        this.studentRepository = studentRepository;
        this.batchRepository = batchRepository;
    }

    @Override
    public Student findStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(!optionalStudent.isPresent()){
            throw new StudentNotFoundException(id);
        }
        return optionalStudent.get();
    }

    @Override
    public List<StudentListItemDTO> getStudentListDTO() {
        List<Student> students = studentRepository.findAll();
        List<StudentListItemDTO> studentListItemDTOList = new ArrayList<>();
        students.forEach(student -> studentListItemDTOList.add(
                StudentListItemDTO.builder()
                .id(student.getId())
                .fullName(student.getAccount().getUserDetail().getFullName())
                .batchName(student.getBatch().getName())
                .email(student.getAccount().getEmail())
                .numberOfClasses(student.getBClasses().size())
                .build()
        ));
        return studentListItemDTOList;
    }

    @Override
    public void changeBatch(Long id, Long batchId) {
        Optional<Batch> optionalBatch = batchRepository.findById(batchId);
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalBatch.isPresent() && optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            student.setBatch(optionalBatch.get());
            studentRepository.save(student);
        }
    }
}
