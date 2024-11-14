package com.example.projectservice.mapper;

import com.example.projectservice.dto.ProjectDto;
import com.example.projectservice.entity.Project;

public class ProjectMapper {
    public static Project mapToProject(ProjectDto dto)
    {
        return Project.builder().name(dto.getName())
                .endDate(dto.getEndDate())
                .status(dto.getStatus())
                .startDate(dto.getStartDate())
                .description(dto.getDescription())
                .build();
    }

}
