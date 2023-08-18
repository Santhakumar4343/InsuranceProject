package com.insurance.util;
import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.internet.MimeMessage;

@Component
public class MailSending {
    @Autowired
    private JavaMailSender mailSender;

    public boolean sendMailWithAttachment(String subject, String body, String to, MultipartFile file) throws Exception {
        MimeMessage mimeMsg = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true); // true indicates multipart message
        helper.setSubject(subject);
        helper.setText(body);
        helper.setTo(to);
        helper.addAttachment("plans.xls", new ByteArrayResource(file.getBytes()));
        mailSender.send(mimeMsg);

        return true;
    }
}

