package com.example.projectservice.service.project_task;

import com.example.projectservice.dto.ProjectTaskDto;
import com.example.projectservice.dto.UserDto;
import com.example.projectservice.entity.ProjectTask;

import java.util.List;

public interface IProjectTaskService {
    List<ProjectTask> getAllProjectTasks();
    List<ProjectTask> getProjectTasksByProjectId(Long projectId);
    ProjectTask createProjectTask(ProjectTaskDto dto);
    void doneProjectTask(Long projectTaskId);
    void assignTo(Long projectTaskId, Long userId);
    void assignTo(Long projectTaskId, String email);
    void assignTo(Long projectTaskId, List<String> emails);
    void deleteTask(Long projectTaskId);
    ProjectTask editProjectTask(ProjectTaskDto dto);
    List<UserDto> getUsersFromTaskId(Long taskId);
}
