package com.example.authservice.controller;

import com.example.authservice.entity.AppUser;
import com.example.authservice.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AppUserController {

    private final IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody AppUser user)
    {
        authService.signUp(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
