package com.example.authservice.service;

import com.example.authservice.dto.AuthResponse;
import com.example.authservice.dto.SignInDto;
import com.example.authservice.entity.AppUser;

public interface IAuthService {
    AuthResponse signIn(SignInDto dto);
    void signUp(AppUser user);
}
