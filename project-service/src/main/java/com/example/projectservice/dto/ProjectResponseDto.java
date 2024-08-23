package com.example.projectservice.dto;

import com.example.projectservice.entity.ProjectTask;
import com.example.projectservice.entity.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Status status  ;
    private Long hostId;
    private List<AppUserDto> members ;
    private List<ProjectTask> projectTasks;
}
