package com.example.notificationservice.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String emailQueue = "email-queue";
    public static final String dlxQueue = "email-dlx-queue";
    public static final String exchange = "exchange";
    public static final String routingKey = "routing";
    public static final String dlxExchange = "dlx-exchange";
    public static final String dlxRoutingKey = "dlx-routing";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(emailQueue)
                .withArgument("x-dead-letter-exchange", dlxExchange)
                .withArgument("x-dead-letter-routing-key", dlxRoutingKey)
                .build();
    }

    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable(dlxQueue)
                .withArgument("x-dead-letter-exchange", exchange)
                .withArgument("x-dead-letter-routing-key", routingKey)
                .withArgument("x-message-ttl", 900000)
                .build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public TopicExchange dlxExchange() {
        return new TopicExchange(dlxExchange);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Binding dlxBinding() {
        return BindingBuilder
                .bind(dlxQueue())
                .to(dlxExchange())
                .with(dlxRoutingKey);
    }
}
