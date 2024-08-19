package com.example.projectservice.service;

import com.example.projectservice.dto.ProjectDto;
import com.example.projectservice.entity.Project;

import java.util.List;

public interface IProjectService {
    List<Project> getAllProjects();
    Project createProject(ProjectDto dto);
    //List<Project> getProjectsByUser(Long userId);
}
