package com.example.authservice.controller;

import com.example.authservice.dto.KeycloakAccessToken;
import com.example.authservice.entity.AppUser;
import com.example.authservice.service.IAuthService;
import com.example.authservice.service.KeycloakService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
class AuthController {

    private final IAuthService authService;
    private final KeycloakService keycloakService;


    @GetMapping("/signin")
    public void signIn(HttpServletResponse response) {
        authService.signIn(response);
    }

    @GetMapping("/callback")
    public ResponseEntity<?> handleKeycloakCallback(@RequestParam("code") String code, HttpServletRequest request) {
        try {
            KeycloakAccessToken token = keycloakService.exchangeCodeForToken(code);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to exchange code for token");
        }
    }
}
