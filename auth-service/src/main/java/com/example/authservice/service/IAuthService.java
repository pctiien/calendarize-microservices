package com.example.authservice.service;

import com.example.authservice.dto.KeycloakAccessToken;
import com.example.authservice.dto.SignInDto;
import com.example.authservice.entity.AppUser;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {
    void signIn(HttpServletResponse response);
    void signUp(AppUser user);
}
