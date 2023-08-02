package com.social.server.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

public interface EmailSenderService {
    void sendEmail(String toEmail,
                   String subject,
                   String body);
    void sendEmailWithAttachment(String toEmail,
                                 String subject,
                                 String body,String pathToFile) throws MessagingException;
}
