package com.example.notificationservice.rabbitmq;

import com.example.notificationservice.dto.EmailDetails;
import com.example.notificationservice.service.IEmailService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);
    private final IEmailService emailService;

    @RabbitListener(queues = {RabbitMQConfig.emailQueue}, ackMode = "MANUAL")
    public void sendEmail(EmailDetails emailDetails, Message message, Channel channel) {
        try {
            LOGGER.info("Received message from RabbitMQ: {}", emailDetails);
            emailService.sendWelcomeEmail(emailDetails);
            // Confirm message are successfully handled
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            LOGGER.error("Failed to send email to {}: {}", emailDetails.getRecipient(), e.getMessage());
            try {
                // return NACK requeue false
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException ex) {
                LOGGER.error("Failed to nack message: {}", ex.getMessage());
            }
        }
    }
}
