package com.example.lifetaskservice.dto;

import com.example.lifetaskservice.entity.Status;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class LifeTaskDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Status status = Status.PENDING;
    private Long userId;
}
