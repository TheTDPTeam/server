package com.tdpteam.service.interf;

import org.springframework.scheduling.annotation.Async;

public interface MailService {
    @Async
    void sendMail(String to, String subject, String content);
}
