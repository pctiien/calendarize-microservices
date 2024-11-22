package com.example.projectservice.repository;

import com.example.projectservice.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("SELECT p FROM Project p JOIN ProjectUser pu ON pu.project.id = p.id WHERE pu.userId = :userId")
    Optional<List<Project>> findAllByUserId(Long userId);

}
