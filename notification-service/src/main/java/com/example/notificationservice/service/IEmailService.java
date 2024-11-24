package com.example.notificationservice.service;

import com.example.notificationservice.dto.EmailDetails;

public interface IEmailService {
    void sendWelcomeEmail(EmailDetails emailDetails);
}
