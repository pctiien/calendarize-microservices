package com.example.authservice.controller;

import com.example.authservice.dto.*;

import com.example.authservice.entity.AuthProvider;
import com.example.authservice.entity.User;
import com.example.authservice.exception.ResourceNotFoundException;
import com.example.authservice.exception.UserAlreadyExistsException;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.TokenProvider;
import com.example.authservice.service.IAuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId)
    {
        return ResponseEntity.ok(authService.getUserById(userId));
    }
    @GetMapping(value = "/users",params = "email")
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(authService.getUserByEmail(email));
    }
    @GetMapping(value = "/users",params = "emails")
    public ResponseEntity<List<UserResponse>> getUsersByEmails(@RequestParam("emails") List<String> emails)
    {
        return ResponseEntity.ok(authService.getUserByEmail(emails));
    }

}
