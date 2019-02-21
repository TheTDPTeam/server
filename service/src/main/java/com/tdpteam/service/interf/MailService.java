package com.tdpteam.service.interf;

import com.sendgrid.Content;
import com.tdpteam.repo.entity.BClass;
import org.springframework.scheduling.annotation.Async;

public interface MailService {
    @Async
    void sendMail(String to, String subject, Content content);
}
