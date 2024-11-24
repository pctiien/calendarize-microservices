package com.example.authservice.rabbitmq;

import com.example.authservice.dto.EmailDetails;
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

    public void sendEmail(EmailDetails emailDetails){
        LOGGER.info("Sending message to RabbitMQ");
        rabbitTemplate.convertAndSend(RabbitMQConst.exchange, RabbitMQConst.routingKey, emailDetails);
    }
}
