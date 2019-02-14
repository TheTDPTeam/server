package com.tdpteam.service.impl;

import com.tdpteam.repo.dto.bClass.BClassDTO;
import com.tdpteam.repo.dto.bClass.BClassListItemDTO;
import com.tdpteam.repo.entity.BClass;
import com.tdpteam.repo.repository.BClassRepository;
import com.tdpteam.service.interf.BClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BClassServiceImpl implements BClassService {
    private BClassRepository bClassRepository;

    @Autowired
    public BClassServiceImpl(BClassRepository bClassRepository) {
        this.bClassRepository = bClassRepository;
    }

    @Override
    public List<BClassListItemDTO> getAllBClassesForRendering() {
        return null;
    }

    @Override
    public BClass getBClassById(Long id) {
        return null;
    }

    @Override
    public void createBClass(BClassDTO bClassDTO) {

    }
}
