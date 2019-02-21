package com.tdpteam.service.impl;

import com.sendgrid.*;
import com.tdpteam.repo.entity.BClass;
import com.tdpteam.service.helper.Constants;
import com.tdpteam.service.interf.MailService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MailServiceImpl implements MailService {

    @Override
    public void sendMail(String to, String subject, Content content) {
        Mail mail = new Mail(new Email("duy2112@gmail.com"), subject, new Email(to), content);
        Request request = new Request();
        SendGrid emailSender = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = emailSender.api(request);
            System.out.println("Status code sending email:" + response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println("Email was not sent." + ex.getLocalizedMessage());
        }
    }
}
