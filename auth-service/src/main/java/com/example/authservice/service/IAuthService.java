package com.example.authservice.service;

import com.example.authservice.dto.*;
import jakarta.servlet.http.HttpServletResponse;


public interface IAuthService {

    AuthResponse authenticateUser(LoginRequest loginRequest, HttpServletResponse response);

    ApiResponse registerUser(SignUpRequest signUpRequest);

    UserResponse getUserById(Long id);

}
