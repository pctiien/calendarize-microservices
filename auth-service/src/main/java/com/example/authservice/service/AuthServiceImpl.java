package com.example.authservice.service;

import com.example.authservice.dto.AuthResponse;
import com.example.authservice.dto.SignInDto;
import com.example.authservice.entity.AppUser;
import com.example.authservice.exception.UserAlreadyExistsException;
import com.example.authservice.repository.AppUserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService{

    private final AppUserRepository appUserRepository;

    @Override
    public AuthResponse signIn(SignInDto dto) {
        return null;
    }

    @Override
    public void signUp(AppUser user) {
        Optional<AppUser> userOptional = appUserRepository.findAppUserByEmail(user.getEmail());
        if(userOptional.isPresent()) throw new UserAlreadyExistsException("User already exists with email :" + user.getEmail());
        appUserRepository.save(user);
    }
}
