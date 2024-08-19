package com.example.appuserservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name = "user")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppUser{
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
}
