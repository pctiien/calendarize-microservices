package com.example.authservice.service.authService;

import com.example.authservice.email.EmailDetails;
import com.example.authservice.email.EmailSubject;
import com.example.authservice.dto.*;
import com.example.authservice.entity.AuthProvider;
import com.example.authservice.entity.Role;
import com.example.authservice.entity.RoleName;
import com.example.authservice.entity.User;
import com.example.authservice.exception.ResourceNotFoundException;
import com.example.authservice.exception.UserAlreadyExistsException;
import com.example.authservice.rabbitmq.RabbitMQProducer;
import com.example.authservice.repository.RoleRepository;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService{
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final RoleRepository roleRepository ;
    private final RabbitMQProducer rabbitMQProducer;
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
                .authProvider(AuthProvider.local)
                .roles(new HashSet<>())
                .build();
        Role userRole = roleRepository.findRoleByName(RoleName.USER).orElseGet(()->roleRepository.save(new Role(RoleName.USER)));

        user.assignRole(userRole);

        userRepository.save(user);

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setRecipient(user.getEmail());
        emailDetails.setSubject(EmailSubject.WELCOME_SUBJECT);
        emailDetails.setMsgBody(user.getName());
        rabbitMQProducer.sendEmail(emailDetails);

        return new ApiResponse(true, "User registered successfully");
    }

    public UserDto getUserById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName()).build();

    }





}
