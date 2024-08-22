package com.example.projectservice.client;

import com.example.projectservice.dto.AppUserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "appuser-service")
public interface AppUserClient {
    @GetMapping("/api/user/{userId}")
    ResponseEntity<AppUserDto> getUserById(@PathVariable Long userId);
}
