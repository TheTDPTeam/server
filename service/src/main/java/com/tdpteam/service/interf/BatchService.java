package com.tdpteam.service.interf;

import com.tdpteam.repo.dto.SelectionItem;
import com.tdpteam.repo.dto.batch.BatchDTO;
import com.tdpteam.repo.dto.batch.BatchListItemDTO;
import com.tdpteam.repo.entity.Batch;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface BatchService extends ActivationService{
    List<BatchListItemDTO> getAllBatchesForRendering();

    Batch getBatchById(Long id);

    @Async
    void createBatch(BatchDTO batchDTO);

    void deleteSubject(Long id);

    List<SelectionItem> getAllBatchesForSelection();
}
