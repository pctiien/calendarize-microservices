package com.example.projectservice.service;

import com.example.projectservice.dto.ProjectDto;
import com.example.projectservice.entity.Project;
import com.example.projectservice.mapper.ProjectMapper;
import com.example.projectservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService{
    private final ProjectRepository projectRepository;
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project createProject(ProjectDto dto) {
        Project project = ProjectMapper.mapToProject(dto);
        projectRepository.save(project);
        return project;
    }

//    @Override
//    public List<Project> getProjectsByUser(Long userId) {
//        return List.of();
//    }
}
