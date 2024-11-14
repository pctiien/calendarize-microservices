package com.example.projectservice.repository;

import com.example.projectservice.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("SELECT p FROM Project p WHERE p.id IN (SELECT pu.project.id FROM ProjectUser pu WHERE pu.userId = :userId)")
    Optional<List<Project>> findAllByUserId(Long userId);

}
