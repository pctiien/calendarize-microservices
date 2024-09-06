package com.example.projectservice.service.project;

import com.example.projectservice.dto.ProjectDto;
import com.example.projectservice.dto.ProjectResponseDto;
import com.example.projectservice.entity.Project;

import java.time.LocalDate;
import java.util.List;

public interface IProjectService {
    List<Project> getAllProjects();
    Project createProject(ProjectDto dto);
    ProjectResponseDto getProjectByProjectId(Long projectId);
    ProjectResponseDto getProjectByIdBetween(Long projectId, LocalDate from, LocalDate to);
    List<ProjectDto> getProjectsByUserId(Long userId);
    void addMemberToProject(Long projectId,Long userId);
}
