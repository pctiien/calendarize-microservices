package com.example.lifetaskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class LifetaskServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LifetaskServiceApplication.class, args);
    }

}
