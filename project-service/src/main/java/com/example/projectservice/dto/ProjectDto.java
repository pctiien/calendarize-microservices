package com.example.projectservice.dto;

import com.example.projectservice.entity.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Status status = Status.PENDING ;
    private Long hostId;
}
