package com.example.authservice.controller;

import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.User;
import com.example.authservice.service.userService.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or (isAuthenticated() and hasAuthority('USER') and principal.id == #userId)")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping(params = "email")
    @PreAuthorize("(isAuthenticated() and hasAuthority('USER')) or hasAuthority('ADMIN') ")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam(value = "email",required = true) String email){
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('ADMIN') or (isAuthenticated() and principal.email == #dto.email)")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto dto){
        return ResponseEntity.ok(userService.updateUser(dto));
    }





}
