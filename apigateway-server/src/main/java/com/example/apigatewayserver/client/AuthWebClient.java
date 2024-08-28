package com.example.apigatewayserver.client;


import com.example.apigatewayserver.security.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Component
public class AuthWebClient {

    private final WebClient webClient;

    public AuthWebClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://auth-service").build();
    }

    public Mono<User> getUserById(Long userId) {
        return webClient.get()
                .uri("/api/auth/users/{userId}", userId)
                .retrieve()
                .bodyToMono(User.class);
    }

}

