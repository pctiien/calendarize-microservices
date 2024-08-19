package com.example.projectservice.mapper;

import com.example.projectservice.dto.ProjectTaskDto;
import com.example.projectservice.entity.Project;
import com.example.projectservice.entity.ProjectTask;

public class ProjectTaskMapper {
    public static ProjectTask mapToProjectTask(ProjectTaskDto dto)
    {
        return ProjectTask.builder().name(dto.getName())
                .status(dto.getStatus())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .build();
    }
}
