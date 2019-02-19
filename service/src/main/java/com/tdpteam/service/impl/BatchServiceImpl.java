package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.SelectionItem;
import com.tdpteam.repo.dto.batch.BatchDTO;
import com.tdpteam.repo.dto.batch.BatchListItemDTO;
import com.tdpteam.repo.entity.Batch;
import com.tdpteam.repo.entity.Course;
import com.tdpteam.repo.repository.BatchRepository;
import com.tdpteam.repo.repository.CourseRepository;
import com.tdpteam.service.exception.batch.BatchNotFoundException;
import com.tdpteam.service.exception.course.CourseNotFoundException;
import com.tdpteam.service.interf.BatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BatchServiceImpl implements BatchService {
    private BatchRepository batchRepository;
    private CourseRepository courseRepository;
    private ModelMapper modelMapper;

    @Autowired
    public BatchServiceImpl(BatchRepository batchRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.batchRepository = batchRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BatchListItemDTO> getAllBatchesForRendering() {
        List<Batch> batches = batchRepository.findAllByOrderByCreatedAtDesc();
        List<BatchListItemDTO> batchListItemDTOList = new ArrayList<>();
        batches.forEach(batch -> batchListItemDTOList.add(
                BatchListItemDTO.builder()
                        .id(batch.getId())
                        .name(batch.getName())
                        .code(batch.getCode())
                        .courseCode(batch.getCourse().getCode())
                        .numberOfStudents(batch.getStudents().size())
                        .startDate(batch.getStartDate())
                        .isActivated(batch.isActivated())
                        .build()
        ));
        return batchListItemDTOList;
    }

    @Override
    public Batch getBatchById(Long id) {
        Optional<Batch> optionalBatch = batchRepository.findById(id);
        if(!optionalBatch.isPresent()){
            throw new BatchNotFoundException(id);
        }
        return optionalBatch.get();
    }

    @Override
    public void createBatch(BatchDTO batchDTO) {
        Optional<Course> optionalCourse = courseRepository.findById(batchDTO.getCourseId());
        if(!optionalCourse.isPresent()){
            throw new CourseNotFoundException(batchDTO.getCourseId());
        }
        Batch newBatch = modelMapper.map(batchDTO, Batch.class);
        newBatch.setCourse(optionalCourse.get());
        batchRepository.save(newBatch);
    }

    @Override
    public void deleteSubject(Long id) {
        try{
            batchRepository.deleteById(id);
        }catch (Exception ignored){}
    }

    @Override
    public List<SelectionItem> getAllBatchesForSelection() {
        List<Batch> batches = batchRepository.findAllByOrderByCreatedAtDesc();
        List<SelectionItem> selectionItems = new ArrayList<>();
        batches.forEach(batch -> selectionItems.add(
                new SelectionItem(batch.getId(), batch.getName())
        ));
        return selectionItems;
    }

    @Override
    public String redirectToBatchList() {
        return "redirect:/cms/batches";
    }

    @Override
    public void changeActivation(Long id) {
        Optional<Batch> optionalBatch = batchRepository.findById(id);
        if (optionalBatch.isPresent()) {
            Batch batch = optionalBatch.get();
            batch.setActivated(!batch.isActivated());
            batchRepository.save(batch);
        }
    }
}
