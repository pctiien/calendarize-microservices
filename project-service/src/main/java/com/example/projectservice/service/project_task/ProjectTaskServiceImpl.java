package com.example.projectservice.service.project_task;

import com.example.projectservice.dto.ProjectTaskDto;
import com.example.projectservice.entity.Project;
import com.example.projectservice.entity.ProjectTask;
import com.example.projectservice.entity.Status;
import com.example.projectservice.exception.ProjectNotFoundException;
import com.example.projectservice.mapper.ProjectTaskMapper;
import com.example.projectservice.repository.ProjectRepository;
import com.example.projectservice.repository.ProjectTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectTaskServiceImpl implements IProjectTaskService{
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectRepository projectRepository;
    @Override
    public List<ProjectTask> getAllProjectTasks() {
        return projectTaskRepository.findAll();
    }

    @Override
    public List<ProjectTask> getProjectTasksByProjectId(Long projectId) {
        return projectTaskRepository.findAllByProjectId(projectId).orElse(new ArrayList<>());
    }

    @Override
    public ProjectTask createProjectTask(ProjectTaskDto dto) {
        Project project =  projectRepository.findById(dto.getProjectId()).orElseThrow(()-> new ProjectNotFoundException("id",dto.getProjectId().toString()));
        ProjectTask projectTask = ProjectTaskMapper.mapToProjectTask(dto);
        projectTask.setProject(project);
        projectTaskRepository.save(projectTask);
        return projectTask;
    }

    @Override
    public void doneProjectTask(Long projectTaskId) {
        ProjectTask projectTask = projectTaskRepository.findById(projectTaskId).orElseThrow(()-> new ProjectNotFoundException("id",projectTaskId.toString()));
        projectTask.setStatus(Status.COMPLETED);
        projectTaskRepository.save(projectTask);
    }
}
