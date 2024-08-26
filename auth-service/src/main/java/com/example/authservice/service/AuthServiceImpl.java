package com.example.authservice.service;

import com.example.authservice.dto.KeycloakAccessToken;
import com.example.authservice.dto.SignInDto;
import com.example.authservice.entity.AppUser;
import com.example.authservice.exception.UserAlreadyExistsException;
import com.example.authservice.repository.AppUserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService{

    private final AppUserRepository appUserRepository;

    private final KeycloakService keycloakService;

    @Override
    public void signIn(HttpServletResponse response) {
        try {
            response.sendRedirect(keycloakService.getAuthorizationUrl());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void signUp(AppUser user) {
        Optional<AppUser> userOptional = appUserRepository.findAppUserByEmail(user.getEmail());
        if(userOptional.isPresent()) throw new UserAlreadyExistsException("User already exists with email :" + user.getEmail());
        appUserRepository.save(user);
    }
}
