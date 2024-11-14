package com.example.projectservice.repository;

import com.example.projectservice.entity.Project;
import com.example.projectservice.entity.ProjectRole;
import com.example.projectservice.entity.ProjectRoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRoleRepository extends JpaRepository<ProjectRole,Long> {
    ProjectRole findByName(ProjectRoleName name);
}
