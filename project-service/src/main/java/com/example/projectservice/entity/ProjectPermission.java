package com.example.projectservice.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "project_permission")
@Data
public class ProjectPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


}
