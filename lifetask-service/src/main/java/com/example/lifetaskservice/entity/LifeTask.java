package com.example.lifetaskservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "life_task")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LifeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING ;
    @Column(name = "user_id")
    private Long userId;
}
