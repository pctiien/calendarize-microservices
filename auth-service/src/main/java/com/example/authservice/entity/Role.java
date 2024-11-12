package com.example.authservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "roles")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "role_name",unique = true)
    private RoleName name;

    public Role(RoleName roleName) {
        this.name = roleName;
    }
}
