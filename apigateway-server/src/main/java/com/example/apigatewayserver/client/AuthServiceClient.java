package com.example.apigatewayserver.client;

import com.example.apigatewayserver.security.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {
    @GetMapping("api/auth/users/{userId}")
    ResponseEntity<User> getUserById(@PathVariable("userId") Long id);
}
