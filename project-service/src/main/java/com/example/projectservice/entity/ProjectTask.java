package com.example.projectservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "project_task")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProjectTask {
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
    private Status status = Status.PENDING;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}
