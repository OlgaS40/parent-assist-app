package com.parentapp.auth.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Async
    public void sendEmail(MimeMessage email) {
        javaMailSender.send(email);
    }

    public MimeMessage createMimeMessage(){
        return javaMailSender.createMimeMessage();
    }
}
