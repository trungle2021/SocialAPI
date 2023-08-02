package com.social.server.services.implement;

import com.social.server.services.EmailSenderService;
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

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    @Value("${spring.mail.username}")
    private String emailFrom;
    private final JavaMailSender javaMailSender;

    @Async
    @Override
    public void sendEmail(String toEmail,
                          String subject,
                          String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);

    }

    @Async
    @Override
    public void sendEmailWithAttachment(String toEmail,
                                        String subject,
                                        String body,
                                        String pathToFile) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom(emailFrom);
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body);
        FileSystemResource fileSystem = new FileSystemResource(new File(pathToFile));
        mimeMessageHelper.addAttachment(fileSystem.getFilename(),fileSystem);
        javaMailSender.send(mimeMessage);

    }

}
