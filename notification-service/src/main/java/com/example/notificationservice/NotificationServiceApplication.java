package com.example.notificationservice;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().directory("notification-service/.env").load();
        System.setProperty("MAIL_USERNAME", Objects.requireNonNull(dotenv.get("MAIL_USERNAME")));
        System.setProperty("MAIL_PASSWORD", Objects.requireNonNull(dotenv.get("MAIL_PASSWORD")));
        SpringApplication.run(NotificationServiceApplication.class, args);

    }

}
