package com.example.notificationservice.service.impl;

import com.example.notificationservice.dto.EmailDetails;
import com.example.notificationservice.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {


    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.from.email}")
    private String from ;
    private final JavaMailSender mailSender;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    @Override
    public void sendWelcomeEmail(EmailDetails emailDetails) {

        // Load html template
        Context context = new Context();
        context.setVariable("name", emailDetails.getMsgBody());
        String htmlContent = templateEngine.process("welcomeEmail/welcomeEmailHtml.html",context);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(emailDetails.getRecipient());
            helper.setSubject(emailDetails.getSubject());
            helper.setFrom(from);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch ( MessagingException e) {
            e.printStackTrace();
        }
        LOGGER.info(String.format("Sending email to %s",emailDetails.getRecipient()));
        mailSender.send(message);
    }
}
