package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.bClass.BClassDTO;
import com.tdpteam.repo.dto.bClass.BClassListItemDTO;
import com.tdpteam.repo.entity.BClass;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface BClassService {
    List<BClassListItemDTO> getAllBClassesForRendering();

    BClass getBClassById(Long id);

    @Async
    void createBClass(BClassDTO bClassDTO);
}
