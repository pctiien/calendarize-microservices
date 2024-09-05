package com.example.projectservice.repository;

import com.example.projectservice.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query("Select p from Project p join ProjectTask pt on p.id = pt.project.id join TaskMember tm on pt.id = tm.taskId where tm.userId = :userId")
    Optional<List<Project>> findAllByUserId(Long userId);

}
