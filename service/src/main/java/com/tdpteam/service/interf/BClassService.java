package com.tdpteam.service.interf;

import com.sendgrid.Content;
import com.tdpteam.repo.dto.bClass.BClassDTO;
import com.tdpteam.repo.dto.bClass.BClassDetailDTO;
import com.tdpteam.repo.dto.bClass.BClassListItemDTO;
import com.tdpteam.repo.entity.BClass;
import org.springframework.scheduling.annotation.Async;

import java.util.Date;
import java.util.List;

public interface BClassService extends ActivationService{
    List<BClassListItemDTO> getAllBClassesForRendering();

    BClass getBClassById(Long id);

    @Async
    void createBClass(BClassDTO bClassDTO);

    void deleteCourse(Long id);

    BClassDetailDTO getBClassDetail(Long id);

    String redirectToClassList();

    @Async
    void sendBClassCreateNotificationEmail(BClass bClass);

    Content getEmailContentForClassCreation(String studentName, String subjectName, String startDate, String teacherName, String calendar);
}
