package com.social.server.services;

import jakarta.mail.MessagingException;

import java.io.File;

public interface EmailSenderService {
    void sendEmail(String toEmail,
                   String subject,
                   String body);
    void sendEmailWithAttachment(String toEmail,
                                 String subject,
                                 String body,String pathToFile) throws MessagingException;
}
