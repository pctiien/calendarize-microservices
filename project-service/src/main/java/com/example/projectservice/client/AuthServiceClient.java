package com.example.projectservice.client;

import com.example.projectservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {
    @GetMapping("/api/auth/users/{userId}")
    ResponseEntity<UserDto> getUserById(@PathVariable Long userId);
    @GetMapping(value = "/api/auth/users",params = "email")
    ResponseEntity<UserDto> getUserByEmail(@RequestParam("email") String email) ;
}
