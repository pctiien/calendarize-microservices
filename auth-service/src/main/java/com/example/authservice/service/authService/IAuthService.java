package com.example.authservice.service.authService;

import com.example.authservice.dto.*;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;


public interface IAuthService {

    AuthResponse authenticateUser(LoginRequest loginRequest, HttpServletResponse response);

    ApiResponse registerUser(SignUpRequest signUpRequest);

}
