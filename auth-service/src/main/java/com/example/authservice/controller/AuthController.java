package com.example.authservice.controller;

import com.example.authservice.dto.*;

import com.example.authservice.service.authService.IAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
class AuthController {

    private final IAuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response)
    {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest,response));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest)
    {
        return ResponseEntity.ok()
                .body(authService.registerUser(signUpRequest));
    }




}
