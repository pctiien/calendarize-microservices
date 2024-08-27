package com.example.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users",
    uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    @Column(name="pwd")
    private String password;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;
}