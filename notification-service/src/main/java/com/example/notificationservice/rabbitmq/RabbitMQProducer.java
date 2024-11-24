package com.example.notificationservice.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public void sendEmail(String message){
        LOGGER.info("Sending message to RabbitMQ");
        rabbitTemplate.convertAndSend(RabbitMQConfig.exchange,RabbitMQConfig.routingKey, message);
    }
}
