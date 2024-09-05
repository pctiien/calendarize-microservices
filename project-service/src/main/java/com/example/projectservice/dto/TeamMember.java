package com.example.projectservice.dto;

import com.example.projectservice.entity.ProjectTask;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TeamMember {
    private Long id;
    private String name;
    private String email;
    private List<ProjectTask> projectTasks;
}
