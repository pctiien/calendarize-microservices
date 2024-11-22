package com.example.projectservice.service.project_task;

import com.example.projectservice.client.AuthServiceClient;
import com.example.projectservice.dto.ProjectTaskDto;
import com.example.projectservice.dto.UserDto;
import com.example.projectservice.entity.Project;
import com.example.projectservice.entity.ProjectTask;
import com.example.projectservice.entity.ProjectUser;
import com.example.projectservice.entity.Status;
import com.example.projectservice.exception.ProjectNotFoundException;
import com.example.projectservice.exception.ProjectUserNotFoundException;
import com.example.projectservice.exception.TaskNotFoundException;
import com.example.projectservice.exception.UserNotFoundException;
import com.example.projectservice.mapper.ProjectTaskMapper;
import com.example.projectservice.repository.ProjectRepository;
import com.example.projectservice.repository.ProjectTaskRepository;
import com.example.projectservice.repository.ProjectUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProjectTaskServiceImpl implements IProjectTaskService{
    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectRepository projectRepository;
    private final AuthServiceClient authServiceClient;
    private final ProjectUserRepository projectUserRepository;
    @Override
    public List<ProjectTask> getAllProjectTasks() {
        return projectTaskRepository.findAll();
    }

    @Override
    public List<ProjectTask> getProjectTasksByProjectId(Long projectId) {
        return projectTaskRepository.findAllByProjectId(projectId).orElse(new ArrayList<>());
    }

    @Transactional
    @Override
    public ProjectTask createProjectTask(ProjectTaskDto dto) {
        Project project =  projectRepository.findById(dto.getProjectId()).orElseThrow(()-> new ProjectNotFoundException("id",dto.getProjectId().toString()));
        ProjectTask projectTask = ProjectTaskMapper.mapToProjectTask(dto);
        projectTask.setProject(project);
        projectTask = projectTaskRepository.save(projectTask);
        return projectTask;
    }

    @Transactional
    @Override
    public void doneProjectTask(Long projectTaskId) {
        ProjectTask projectTask = projectTaskRepository.findById(projectTaskId).orElseThrow(()-> new ProjectNotFoundException("id",projectTaskId.toString()));
        projectTask.setStatus(Status.COMPLETED);
        projectTaskRepository.save(projectTask);
    }

    @Transactional
    @Override
    public void assignTo(Long projectTaskId, Long userId) {

        ProjectTask projectTask = projectTaskRepository.findById(projectTaskId).orElseThrow(()-> new TaskNotFoundException("id",projectTaskId.toString()));
        ProjectUser projectUser = projectUserRepository.findByProjectAndUserId(projectTask.getProject(),userId).orElseThrow(()-> new ProjectUserNotFoundException("id",userId.toString()));
        projectTask.assignToMember(projectUser);
    }
    @Override
    public void assignTo(Long projectTaskId, String email) {
        ProjectTask projectTask = projectTaskRepository.findById(projectTaskId).orElseThrow(()-> new TaskNotFoundException("id",projectTaskId.toString()));
        UserDto userDto = authServiceClient.getUserByEmail(email).getBody();
        if(userDto!=null)
        {
            ProjectUser projectUser = projectUserRepository.findByProjectAndUserId(projectTask.getProject(),userDto.getId()).orElseThrow(()-> new ProjectUserNotFoundException("id",userDto.getId().toString()));
            projectTask.assignToMember(projectUser);
            projectTaskRepository.save(projectTask);
        }else{
            throw new UserNotFoundException("email",email);
        }
    }

    @Override
    public void deleteTask(Long projectTaskId) {
        projectTaskRepository.deleteById(projectTaskId);
    }

    @Override
    public ProjectTask editProjectTask(ProjectTaskDto dto) {
        ProjectTask projectTask = projectTaskRepository.findById(dto.getId()).orElseThrow(()->new TaskNotFoundException("id",dto.getId().toString()));
        ProjectTask newProjectTask = ProjectTaskMapper.mapToProjectTask(dto);
        newProjectTask.setId(projectTask.getId());
        newProjectTask.setProject(projectTask.getProject());
        newProjectTask = projectTaskRepository.save(newProjectTask);
        return newProjectTask;
    }

    @Override
    public List<UserDto> getUsersFromTaskId(Long taskId) {
        ProjectTask projectTask = projectTaskRepository.findById(taskId).orElseThrow(()->new TaskNotFoundException("id",taskId.toString()));
        List<ProjectUser> projectUsers = projectTask.getProjectUsers().stream().toList();
        return projectUsers.stream().map(u->authServiceClient.getUserById(u.getUserId()).getBody()).toList();
    }
}




















