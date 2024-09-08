package com.example.authservice.service;

import com.example.authservice.dto.*;
import com.example.authservice.entity.AuthProvider;
import com.example.authservice.entity.User;
import com.example.authservice.exception.ResourceNotFoundException;
import com.example.authservice.exception.UserAlreadyExistsException;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.TokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService{
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Override
    public AuthResponse authenticateUser(LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication);
        return new AuthResponse(token);
    }

    @Override
    public ApiResponse registerUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail()))
        {
            throw new UserAlreadyExistsException("User already exists with email :" + signUpRequest.getEmail());
        }
        User user = User.builder().name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .authProvider(AuthProvider.local).build();

        userRepository.save(user);

        return new ApiResponse(true, "User registered successfully");
    }

    public UserResponse getUserById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName()).build();

    }

    @Override
    public UserResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User","email",email));
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName()).build();
    }

}
